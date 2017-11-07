//
//  PaymentManager.swift
//  Chapter9
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation
import StoreKit

protocol PaymentManagerProtocol {
    func finishRequest(_ request:SKProductsRequest, products:Array<SKProduct>)
    func finishRequest(_ request:SKRequest, didFailWithError:Error)
    func finishPayment(_ paymentTransaction:SKPaymentTransaction)
    func finishPayment(failed paymentTransaction:SKPaymentTransaction)
    func finishRestore(_ queue:SKPaymentQueue)
    func finishRestore(_ queue:SKPaymentQueue, restoreCompletedTransactionsFailedWithError:Error)
    func finishVerifyReceipt(_ content1ExpiresDate:UInt64, content2ExpiresDate:UInt64)
}

class PaymentManager: NSObject {
    let sharedSecret = "822b130cf55c4eb289111fc5836e9b5c"
    
    // 購入完了のノーティフィケーション
    public static let paymentCompletedNotification = "PaymentCompletedNotification"
    
    // 購入失敗のノーティフィケーション
    public static let paymentErrorNotification = "PaymentErrorNotification"
    
    // トランザクションが残っていることを保存するキー名
    public static let remainTransactionKey = "IsRemainTransaction"
    
    // シングルとして扱う
    static let sharedInstance = PaymentManager()
    
    var delegate:PaymentManagerProtocol?
    
    func startTransactionObserve() {
        print(#function)
        SKPaymentQueue.default().add(self)
    }
    
    func stopTransactionObserve() {
        print(#function)
        SKPaymentQueue.default().remove(self)
    }
    
    func startProductRequest(_ productIds:Set<String>) -> SKProductsRequest {
        print(#function)
        let productsRequest = SKProductsRequest(productIdentifiers: productIds)
        productsRequest.delegate = self
        productsRequest.start()
        
        return productsRequest
    }

    @discardableResult
    func buyProduct(_ product:SKProduct) -> Bool {
        print(#function)
        
        // 購入処理の開始前に、端末の設定がコンテンツを購入することができるようになっているか確認する
        guard SKPaymentQueue.canMakePayments() else {
            return false
        }
        
        let payment = SKPayment(product: product)
        SKPaymentQueue.default().add(payment)

        return true
    }
    
    func startRestore() {
        print(#function)
        SKPaymentQueue.default().restoreCompletedTransactions()
    }
    
    func isRemainTransaction() -> Bool {
        let result = UserDefaults.standard.bool(forKey: PaymentManager.remainTransactionKey)
        return result
    }
    
    func verifyReceipt(_ receipt:String) /* ->  Dictionary<String, Any>*/ {
//        let url = URL.init(string: "https://buy.itunes.apple.com/verifyReceipt")
        if let url = URL.init(string: "https://sandbox.itunes.apple.com/verifyReceipt") {
        
            // 本来はサーバーにレシートを送ってサーバー側でこの処理を行う
            // 自動更新ではない場合はpasswordは不要
            let jsonForTransmission = "{\"receipt-data\": \"\(receipt)\", \"password\":\"\(sharedSecret)\"}"
            print(jsonForTransmission)
            let request = NSMutableURLRequest(url: url)
            request.httpBody = jsonForTransmission.data(using: .utf8)
            request.httpMethod = "POST"
            
            let task = URLSession.shared.dataTask(with: request as URLRequest, completionHandler: { (data , responce, error) in
                if let jsonData = data {
                    do {
                        var content1ExpiresDate: UInt64 = 0
                        var content2ExpiresDate: UInt64 = 0

                        let json = try JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers) as! NSDictionary
                        
                        if let status = json["status"] as? Int64, status != 0 && status != 21006 {
                            self.delegate?.finishVerifyReceipt(0, content2ExpiresDate: 0)
                        }
                        
                        // レシート情報の中から一番新しい有効期限を取り出す
                        if let latestReceiptInfo = json["latest_receipt_info"] as? Array<Any> {
                            
                            for receipt in latestReceiptInfo {
                                if let receipt = receipt as? Dictionary<String, Any> {
                                    if let productId = receipt["product_id"] as? String, productId == "jp.co.shoeisya.Chapter9.content1.7days" || productId == "jp.co.shoeisya.Chapter9.content1.1month"  {
                                        if let expireDate = receipt["expires_date_ms"] as? String {
                                            content1ExpiresDate = content1ExpiresDate > UInt64(expireDate)! ? content1ExpiresDate : UInt64(expireDate)!
                                        }
                                    }

                                    if let productId = receipt["product_id"] as? String, productId == "jp.co.shoeisya.Chapter9.content2.7days" || productId == "jp.co.shoeisya.Chapter9.content2.1month"  {
                                        if let expireDate = receipt["expires_date_ms"] as? String {
                                            content2ExpiresDate = content2ExpiresDate > UInt64(expireDate)! ? content2ExpiresDate : UInt64(expireDate)!
                                        }
                                    }
                                }
                            }
                            self.delegate?.finishVerifyReceipt(content1ExpiresDate, content2ExpiresDate: content2ExpiresDate)
                        } else {
                            self.delegate?.finishVerifyReceipt(0, content2ExpiresDate: 0)
                        }
                    } catch let err as NSError {
                        print(err.localizedDescription)
                    }
                }
            })
            task.resume()
            
        }
    }
    
    // 購入処理完了時
    func completeTransaction(_ transaction:SKPaymentTransaction) {
        
        // 購入が完了したことを通知する
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: PaymentManager.paymentCompletedNotification),
                                        object: transaction)
        delegate?.finishPayment(transaction)
        
        // ペイメントキューに終了を伝えてトランザクションを削除する
        SKPaymentQueue.default().finishTransaction(transaction)
        
    }
    
    // 購入処理失敗時
    func failedTransaction(_ transaction:SKPaymentTransaction) {
        
        // エラーの内容をログに出力する
        if let error = transaction.error as? NSError {
            if error.code == SKError.paymentCancelled.rawValue {
                print("キャンセル \(error.localizedDescription)")
            } else {
                print("エラー \(error.localizedDescription)")
            }
        }
        
        // 購入が失敗したことを通知する
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: PaymentManager.paymentErrorNotification),
                                        object: transaction)
        delegate?.finishPayment(failed: transaction)
        
        // ペイメントキューに終了を伝えてトランザクションを削除する
        SKPaymentQueue.default().finishTransaction(transaction)
    }

}

// MARK: SKPaymentTransactionObserver Methods
extension PaymentManager: SKPaymentTransactionObserver {
    // MARK: SKPaymentTransactionObserver Required Methods
    // トランザクションの状態が変化したときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, updatedTransactions transactions: [SKPaymentTransaction]) {
        print(#function)
        
        for transaction in transactions {
            switch (transaction.transactionState) {
            case .purchased: // 購入処理完了
                print("purchased")
                completeTransaction(transaction)
                break
            case .failed: // 購入処理失敗
                print("failed")
                failedTransaction(transaction)
                break
            case .restored: // リストア
                print("restored")
                completeTransaction(transaction)
                break
            case .deferred: // 保留中
                print("deferred")
                break
            case .purchasing: // 購入処理開始
                print("purchasing")
                
                // トランザクションが開始されたことを記憶しておく
                UserDefaults.standard.set(true, forKey: PaymentManager.remainTransactionKey)
                UserDefaults.standard.synchronize()
                break
            }
        }
    }
    
    // MARK: SKPaymentTransactionObserver Optional Methods
    // トランザクションがfinishTransaction経由でキューから削除されたときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, removedTransactions transactions: [SKPaymentTransaction]) {
        print(#function)
        // トランザクションが終了したことを記憶しておく
        UserDefaults.standard.set(false, forKey: PaymentManager.remainTransactionKey)
        UserDefaults.standard.synchronize()
    }
    
    // ユーザーの購入履歴からキューに戻されたトランザクションを追加中にエラーが発生したときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, restoreCompletedTransactionsFailedWithError error: Error) {
        print(#function)
        delegate?.finishRestore(queue, restoreCompletedTransactionsFailedWithError:error)
    }
    
    // ユーザーの購入履歴から全てのトランザクションが正常に戻され、キューに追加されたときに呼ばれる
    public func paymentQueueRestoreCompletedTransactionsFinished(_ queue: SKPaymentQueue) {
        print(#function)
        
        delegate?.finishRestore(queue)
    }
    
    // ダウンロード状態が変化したときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, updatedDownloads downloads: [SKDownload]) {
        print(#function)
    }
}

// MARK: SKProductsRequestDelegate Methods
extension PaymentManager : SKProductsRequestDelegate {
    
    // プロダクト情報の取得が成功したときに呼び出される
    public func productsRequest(_ request: SKProductsRequest, didReceive response: SKProductsResponse) {
        // invalidProductIdentifiersがあればログに出力する
        for invalidIds in response.invalidProductIdentifiers {
            print(invalidIds)
        }
        
        delegate?.finishRequest(request, products:response.products)
    }
    
    // プロダクト情報の取得が失敗したときに呼び出される
    public func request(_ request: SKRequest, didFailWithError error: Error) {
        print(error.localizedDescription)

        delegate?.finishRequest(request, didFailWithError: error)
    }
}
