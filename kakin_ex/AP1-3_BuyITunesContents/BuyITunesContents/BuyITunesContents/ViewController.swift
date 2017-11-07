//
//  ViewController.swift
//  BuyITunesContents
//
//  Copyright © 2016年 shoeisha. All rights reserved.
//

import UIKit
import StoreKit

class ViewController: UIViewController {
    let app = 590384791
    let developer = 590384794
    let music = 981415599
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func showProductViewController(_ identifier: Int) {
        let parameter:Dictionary = [SKStoreProductParameterITunesItemIdentifier: identifier]
        
        let storeProductViewController = SKStoreProductViewController()
        storeProductViewController.delegate = self
        storeProductViewController.loadProduct(withParameters: parameter, completionBlock: { (result, error) in
            print(result)
            if let error = error {
                print(error.localizedDescription)
            }
        })
        
        present(storeProductViewController, animated: true, completion: nil)
    }
    
    @IBAction func handleAppButton(_ sender: Any) {
        showProductViewController(app)
    }
    
    @IBAction func handleDeveloperButton(_ sender: Any) {
        showProductViewController(developer)
    }
    
    @IBAction func handleMusicButton(_ sender: Any) {
        showProductViewController(music)
    }
}

extension ViewController: SKStoreProductViewControllerDelegate {
    public func productViewControllerDidFinish(_ viewController: SKStoreProductViewController) {
        print(#function)
        viewController.dismiss(animated: true, completion: nil)
    }
}
