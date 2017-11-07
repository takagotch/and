//
//  MyPaymentTransactionObserver.swift
//  Chapter7
//
//  Copyright © 2016年 Shoeisha. All rights reserved.
//

import Foundation
import StoreKit

class MyPaymentTransactionObserver: NSObject {
    // 完了と失敗を伝えるNotificationの名前
    public static let kPaymentCompletedNotification = "PaymentCompletedNotification"
    public static let kPaymentErrorNotification = "PaymentErrorNotification"
  
    // シングルとして扱う
    static let sharedInstance = MyPaymentTransactionObserver()
    
    // MARK: 
    // 購入処理完了時
    func completeTransaction(_ transaction:SKPaymentTransaction) {
        
        // 購入が完了したことを通知する
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: MyPaymentTransactionObserver.kPaymentCompletedNotification),
                                        object: transaction)
        
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
        
        // エラーを通知する
        // 購入が完了したことを通知する
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: MyPaymentTransactionObserver.kPaymentErrorNotification),
                                        object: transaction)
        
        // ペイメントキューに終了を伝えてトランザクションを削除する
        SKPaymentQueue.default().finishTransaction(transaction)
    }
    
}

extension MyPaymentTransactionObserver: SKPaymentTransactionObserver {
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
                break
            case .deferred: // 保留中
                print("deferred")
                break
            case .purchasing: // 購入処理開始
                print("purchasing")
                break
            }
        }
    }
    
    // MARK: SKPaymentTransactionObserver Optional Methods
    // トランザクションがfinishTransaction経由でキューから削除されたときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, removedTransactions transactions: [SKPaymentTransaction]) {
        print(#function)
    }
    
    // ユーザーの購入履歴からキューに戻されたトランザクションを追加中にエラーが発生したときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, restoreCompletedTransactionsFailedWithError error: Error) {
        print(#function)
    }
    
    // ユーザーの購入履歴から全てのトランザクションが正常に戻され、キューに追加されたときに呼ばれる
    public func paymentQueueRestoreCompletedTransactionsFinished(_ queue: SKPaymentQueue) {
        print(#function)
    }
    
    // ダウンロード状態が変化したときに呼ばれる
    public func paymentQueue(_ queue: SKPaymentQueue, updatedDownloads downloads: [SKDownload]) {
        print(#function)
    }
}
