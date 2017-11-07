//
//  ProductViewController.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import UIKit
import StoreKit

class ProductViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    var paymentManager:PaymentManager!
    var productRequest:SKProductsRequest?
    var alert: UIAlertController?
    var products: Array<SKProduct>?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
        
        // プロダクト情報の取得処理中にUIAlertViewを表示させる
        DispatchQueue.main.async {
            let alert = UIAlertController(title: "処理中",
                                          message: "プロダクトリストを取得しています",
                                          preferredStyle: .alert)
            
            self.present(alert, animated: true, completion: nil)
            self.alert = alert
            
        }
        
        // PaymentManagerのインスタンスを保持する
        paymentManager = PaymentManager.sharedInstance
        
        // PaymentManagerのdelegateを設定する
        paymentManager.delegate = self
        
        // ProductManagerからプロダクトIDの一覧（Set）を取得して
        // PaymentManagerにプロダクト情報取得の開始を指示する
        let productIds = ProductManager.sharedInstance.productIds()
        productRequest = paymentManager.startProductRequest(productIds)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
    // Mark: - PaymentNotification Method
    func paymentCompletedNotification(notification: Notification) {
        
    }
    
    func paymentErrorNotification(notification: Notification) {
        
    }
    
}

extension ProductViewController: UITableViewDataSource {
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return products?.count ?? 0
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!
        cell.textLabel?.text = products?[indexPath.row].localizedTitle
        return cell
    }
    
}

extension ProductViewController: UITableViewDelegate {
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        // 保持していたプロダクトのリストから該当のプロダクトを取り出す
        guard let product = products?[indexPath.row] else {
            return
        }
        
        // 購入処理中にUIAlertControllerを表示させる
        let alert = UIAlertController(title: "処理中",
                                      message: "\(product.localizedTitle)の購入処理中です",
            preferredStyle: .alert)
        present(alert, animated: true, completion: nil)
        self.alert = alert
        
        // プロダクトの購入処理を開始させる
        paymentManager.buyProduct(product)
    }
}

extension ProductViewController: PaymentManagerProtocol {
    func finishRequest(_ request:SKProductsRequest, products:Array<SKProduct>) {
        print(#function)
        
        self.products = products
        
        DispatchQueue.main.async {
            self.alert?.dismiss(animated: true, completion: nil)
            self.alert = nil
            self.tableView.reloadData()
        }
    }
    
    func finishRequest(_ request:SKRequest, didFailWithError:Error) {
        print(#function)
        
        DispatchQueue.main.async {
            self.alert?.dismiss(animated: true, completion: nil)
            self.alert = nil
        }
    }
    
    func finishPayment(_ paymentTransaction:SKPaymentTransaction) {
        print(#function)
        
        DispatchQueue.main.async {
            self.alert?.dismiss(animated: true, completion: nil)
            self.alert = nil
            
            let newAlert = UIAlertController(title: "購入処理完了",
                                             message: nil,
                                             preferredStyle: .alert)
            let action = UIAlertAction(title: "OK",
                                       style: .default) { action in
                                        self.alert = nil
            }
            
            newAlert.addAction(action)
            self.present(newAlert, animated: true, completion: nil)
            self.alert = newAlert
        }
    }
    
    func finishPayment(failed paymentTransaction:SKPaymentTransaction) {
        print(#function)
        
        DispatchQueue.main.async {
            self.alert?.dismiss(animated: true, completion: nil)
            self.alert = nil
            
            let newAlert = UIAlertController(title: "購入処理失敗",
                                             message: nil,
                                             preferredStyle: .alert)
            let action = UIAlertAction(title: "OK",
                                       style: .default) { action in
                                        self.alert = nil
            }
            newAlert.addAction(action)
            self.present(newAlert, animated: true, completion: nil)
            self.alert = newAlert
        }
    }
    
    func finishRestore(_ queue:SKPaymentQueue) {}
    
    func finishRestore(_ queue:SKPaymentQueue, restoreCompletedTransactionsFailedWithError:Error) {}
    
}

