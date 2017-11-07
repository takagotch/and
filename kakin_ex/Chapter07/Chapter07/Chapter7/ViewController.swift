//
//  ViewController.swift
//  Chapter7
//
//  Copyright © 2016年 shoeisha. All rights reserved.
//

import UIKit
import StoreKit

class ViewController: UIViewController {
    
    @IBOutlet weak var textView: UITextView!
    @IBOutlet weak var paymentButton: UIButton!
    
    var productsRequest: SKProductsRequest?
    var products: [SKProduct] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // 購入処理が完了した時の通知を受け取るように登録
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(ViewController.paymentCompleted(_:)),
                                               name: NSNotification.Name(rawValue: MyPaymentTransactionObserver.kPaymentCompletedNotification),
                                               object: nil)
        
        // 購入処理が失敗した時の通知を受け取るように登録
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(ViewController.paymentFailed(_:)),
                                               name: NSNotification.Name(rawValue: MyPaymentTransactionObserver.kPaymentErrorNotification),
                                               object: nil)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func tapProductRequestButton(_ sender: AnyObject) {
        
        // プロダクトIDを設定してプロダクト情報の取得を行う
        var productIds = Set<String>()
        productIds.insert("com.yourcompany.app_name.productid")
        let productsRequest = SKProductsRequest(productIdentifiers: productIds)
        productsRequest.delegate = self
        productsRequest.start()
    }
    
    @IBAction func tapPaymentButton(_ sender: AnyObject) {
        
        // 購入処理の開始前に、端末の設定がコンテンツを購入することができるようになっているか確認する
        // 許可されていなければユーザーに通知する
        guard SKPaymentQueue.canMakePayments() else {
            let alert = UIAlertController(title:"エラー",
                                          message: "コンテンツの購入が許可されていません",
                                          preferredStyle: UIAlertControllerStyle.alert)
            let defaultAction:UIAlertAction = UIAlertAction(title: "OK",
                                                            style: UIAlertActionStyle.default,
                                                            handler:nil)
            alert.addAction(defaultAction)
            present(alert, animated: true, completion: nil)
            
            return
        }
        
        for product in products {
            
            // 取得したSKProductを与えてSKPaymentを生成する
            let payment = SKMutablePayment(product: product)
            payment.quantity = 3
            
            // SKPaymentQueueに追加することで購入処理を開始する
            // 購入結果はSKPaymentTransactionObserverで受け取る
            SKPaymentQueue.default().add(payment)
        }
    }
}

// MARK: Notificationを受けたときのメソッド
extension ViewController {
    func paymentCompleted(_ notification: NSNotification) {
        print(#function)
        
        // 実際はここで機能を有効にしたり、コンテンツを表示したりする
        if let transaction = notification.object as? SKPaymentTransaction {
            let message = "\(transaction.payment.productIdentifier) が購入されました"
            let alert = UIAlertController(title: "購入処理完了",
                                          message: message,
                                          preferredStyle: UIAlertControllerStyle.alert)
            let defaultAction:UIAlertAction = UIAlertAction(title: "OK",
                                                            style: UIAlertActionStyle.default,
                                                            handler:nil)
            alert.addAction(defaultAction)
            present(alert, animated: true, completion: nil)
        }
    }
    
    func paymentFailed(_ notification: NSNotification) {
        print(#function)
        
        if let transaction = notification.object as? SKPaymentTransaction {
            var  message = ""
            if let localizedDescription = transaction.error?.localizedDescription {
                message = localizedDescription
            }
            let alert = UIAlertController(title: "購入処理失敗",
                                          message: message,
                                          preferredStyle: UIAlertControllerStyle.alert)
            let defaultAction:UIAlertAction = UIAlertAction(title: "OK",
                                                            style: UIAlertActionStyle.default,
                                                            handler:nil)
            alert.addAction(defaultAction)
            present(alert, animated: true, completion: nil)
        }
    }
}

// MARK: SKProductsRequestDelegate Methods
extension ViewController : SKProductsRequestDelegate {
    
    // プロダクト情報の取得が成功したときに呼び出される
    public func productsRequest(_ request: SKProductsRequest, didReceive response: SKProductsResponse) {
        
        // invalidProductIdentifiersがあればログに出力する
        for invalidIds in response.invalidProductIdentifiers {
            print(invalidIds)
        }
        
        // プロダクト情報を後から参照できるようにメンバ変数に保存しておく
        products = response.products
        
        // 取得したプロダクト情報を順番にUItextVIewに表示する（今回は1つだけ）
        textView.text = ""
        for product in products {
            let info = "\(product.localizedTitle) \(product.localizedDescription) \(product.price) \n"
            textView.text = textView.text + info
        }
        
        // 購入ボタンを有効にする
        paymentButton.isEnabled = true
    }
    
    // プロダクト情報の取得が失敗したときに呼び出される
    public func request(_ request: SKRequest, didFailWithError error: Error) {
        print(error.localizedDescription)
    }
}

