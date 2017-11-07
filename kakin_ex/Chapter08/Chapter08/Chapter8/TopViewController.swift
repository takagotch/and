//
//  TopViewController.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import UIKit
import StoreKit

class TopViewController: UIViewController {
    
    var alert: UIAlertController?
    
    @IBOutlet weak var playersLabel: UILabel!
    @IBOutlet weak var speedupLabel: UILabel!
    @IBOutlet weak var powerupLabel: UILabel!
    @IBOutlet weak var stage3Button: UIButton!
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
        
        if PaymentManager.sharedInstance.isRemainTransaction() {
            let alert = UIAlertController(title: "処理中",
                                          message: "購入処理中です",
                                          preferredStyle: .alert)
            present(alert, animated: true, completion: nil)
            self.alert = alert
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        reflectProductInformation()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func unwind(segue: UIStoryboardSegue) {
    }
    
    @IBAction func handleRestoreButton(_ sender: Any) {
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
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "buy" {
            return
        }
        
        var pattern: [Bool]
        
        if segue.identifier == "stage1" {
            pattern = [false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false]
        } else if segue.identifier == "stage2" {
            pattern = [false,
                       false,
                       false,
                       false,
                       false,
                       true,
                       true,
                       true,
                       true,
                       true,
                       false,
                       false,
                       false,
                       false,
                       false]
        } else {
            pattern = [false,
                       false,
                       true,
                       false,
                       false,
                       false,
                       true,
                       false,
                       true,
                       false,
                       true,
                       false,
                       false,
                       false,
                       true]
        }
        
        if let vc = segue.destination as? GameViewController {
            vc.pattern = pattern
        }
        
        return
    }
    
    func reflectProductInformation() {
        let productManager = ProductManager.sharedInstance
        
        playersLabel.text = "PLAYERS：\(productManager.players)"
        speedupLabel.text = "SPEED UP：\(productManager.speedup)"
        powerupLabel.text = "POWER UP：\(productManager.powerup)"
        
        if productManager.isValidStage3 {
            stage3Button.isEnabled = true
        } else {
            stage3Button.isEnabled = false
        }
    }
    
    // Mark: - PaymentNotification Method
    func paymentCompletedNotification(notification: Notification) {
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
    
    func finishRequest(_ request:SKProductsRequest, products:Array<SKProduct>) {}
    func finishRequest(_ request:SKRequest, didFailWithError:Error) {}
    func finishPayment(_ paymentTransaction:SKPaymentTransaction) {}
    func finishPayment(failed paymentTransaction:SKPaymentTransaction) {}
}

