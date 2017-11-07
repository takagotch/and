//
//  TopViewController.swift
//  Chapter9
//
//  Copyright © 2016年 shoeisha. All rights reserved.
//

import UIKit
import StoreKit

class TopViewController: UIViewController {
    
    var alert: UIAlertController?
    
    @IBOutlet weak var content1Button: UIButton!
    @IBOutlet weak var content2Button: UIButton!
    @IBOutlet weak var contentView: UIWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // PaymentManagerが発行する購入処理が完了した時に通知を受け取るように登録する
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.paymentCompletedNotification(notification:)),
                                               name: NSNotification.Name(rawValue: PaymentManager.paymentCompletedNotification),
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.paymentErrorNotification(notification:)),
                                               name: NSNotification.Name(rawValue: PaymentManager.paymentErrorNotification),
                                               object: nil)
        reflectProductInformation()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        reflectProductInformation()
    }

    @IBAction func unwind(segue: UIStoryboardSegue) {
    }

    @IBAction func handleContent1Button(_ sender: Any) {
        if let url = Bundle.main.url(forResource: "Content1", withExtension: "pdf") {
            let request = URLRequest(url: url)
            contentView.loadRequest(request)
        }
    }
    
    @IBAction func handleContent2Button(_ sender: Any) {
        if let url = Bundle.main.url(forResource: "Content2", withExtension: "pdf") {
            let request = URLRequest(url: url)
            contentView.loadRequest(request)
        }
    }
    
    @IBAction func handlaRestoreButton(_ sender: Any) {
        print(#function)
        
        // リストア処理中はUIAlertControllerを表示する
        let alert = UIAlertController(title: "処理中",
                                      message: "復元処理中です",
                                      preferredStyle: .alert)
        present(alert, animated: true, completion: nil)
        self.alert = alert
        
        // PaymentManagerを通じてStoreKitにリストア処理を開始させる
        let paymentManager = PaymentManager.sharedInstance
        paymentManager.delegate = self
        paymentManager.startRestore()
    }
    
    func reflectProductInformation() {
        let productManager = ProductManager.sharedInstance
        content1Button.isEnabled = productManager.isContent1Enable()
        content2Button.isEnabled = productManager.isContent2Enable()
    }
    
    // Mark: - PaymentNotification Method
    func paymentCompletedNotification(notification: Notification) {
        
        // 更新されたレシートを取得する
        if let receiptURL = Bundle.main.appStoreReceiptURL {
            do {
                // レシートを検証する
                let receiptData = try Data.init(contentsOf: receiptURL, options: .uncached)
                let base64encoded = receiptData.base64EncodedString()
                PaymentManager.sharedInstance.verifyReceipt(base64encoded)
                
            } catch {
                print("error")
            }
        }
    }
    
    func paymentErrorNotification(notification: Notification) {
        let newAlert = UIAlertController(title: "購入処理失敗",
                                         message: nil,
                                         preferredStyle: .alert)
        let action = UIAlertAction(title: "OK",
                                   style: .default) { action in
                                    self.alert = nil
        }
        newAlert.addAction(action)
        present(newAlert, animated: true, completion: nil)
        alert = newAlert
        
    }
}

extension TopViewController: PaymentManagerProtocol {
    
    func finishRestore(_ queue:SKPaymentQueue) {
        
        reflectProductInformation()
        
        alert?.dismiss(animated: true, completion: nil)
        alert = nil
        
        let newAlert = UIAlertController(title: "復元完了",
                                         message: nil,
                                         preferredStyle: .alert)
        let action = UIAlertAction(title: "OK",
                                   style: .default) { action in
                                    self.alert = nil
        }
        newAlert.addAction(action)
        present(newAlert, animated: true, completion: nil)
        alert = newAlert
        
    }
    
    func finishRestore(_ queue:SKPaymentQueue, restoreCompletedTransactionsFailedWithError:Error) {
        
        alert?.dismiss(animated: true, completion: nil)
        alert = nil
        
        let newAlert = UIAlertController(title: "復元失敗",
                                         message: nil,
                                         preferredStyle: .alert)
        let action = UIAlertAction(title: "OK",
                                   style: .default) { action in
                                    self.alert = nil
        }
        newAlert.addAction(action)
        present(newAlert, animated: true, completion: nil)
        alert = newAlert
    }
    
    func finishVerifyReceipt(_ content1ExpiresDate:UInt64, content2ExpiresDate:UInt64) {
        
        ProductManager.sharedInstance.saveExpiresDate(content1ExpiresDate, content2: content2ExpiresDate)

        alert?.dismiss(animated: true, completion: nil)
        alert = nil
        
        let newAlert = UIAlertController(title: "購入処理完了",
                                         message: nil,
                                         preferredStyle: .alert)
        let action = UIAlertAction(title: "OK",
                                   style: .default) { action in
                                    self.alert = nil
        }
        
        newAlert.addAction(action)
        present(newAlert, animated: true, completion: nil)
        alert = newAlert
        
        reflectProductInformation()
    }
    
    func finishRequest(_ request:SKProductsRequest, products:Array<SKProduct>) {}
    func finishRequest(_ request:SKRequest, didFailWithError:Error) {}
    func finishPayment(_ paymentTransaction:SKPaymentTransaction) {}
    func finishPayment(failed paymentTransaction:SKPaymentTransaction) {}
}



