//
//  ViewController.swift
//  Chapter10
//
//  Copyright © 2016年 shoeisha. All rights reserved.
//

import UIKit
import StoreKit

class ViewController: UIViewController {

    @IBOutlet weak var webView: UIWebView!
    
    var paymentManager: PaymentManager?
    var product: SKProduct?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        paymentManager = PaymentManager.sharedInstance
        paymentManager?.delegate = self
        paymentManager?.startProductRequest(["jp.co.shoeisha.Chapter10.content1"])
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func handleBuyButton(_ sender: Any) {
        if let product = product {
            paymentManager?.buyProduct(product)
        }
    }
}

extension ViewController: PaymentManagerProtocol {
    func finishRequest(_ request:SKProductsRequest, products:Array<SKProduct>) {
        if let product = products.first {
            self.product = product
        }
    }
    
    func finishRequest(_ request:SKRequest, didFailWithError:Error) {}
    func finishPayment(_ paymentTransaction:SKPaymentTransaction) {}
    func finishPayment(failed paymentTransaction:SKPaymentTransaction) {}
    func finishRestore(_ queue:SKPaymentQueue) {}
    func finishRestore(_ queue:SKPaymentQueue, restoreCompletedTransactionsFailedWithError:Error) {}
    
    func finishDownload(_ url:URL?) {
        if let url = url {
            webView.loadRequest(URLRequest(url: url))
        }
    }
}
