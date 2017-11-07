//
//  ProductManager.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation
import StoreKit

class ProductManager:NSObject {
    enum userDefaultsKey : String {
        case players = "ProductManagerPlayers"
        case powerup = "ProductManagerPowerup"
        case speedup = "ProductManagerSpeedup"
        case stage3 = "ProductManagerStage3"
    }
    
    // シングルとして扱う
    static let sharedInstance = ProductManager()
    
    let userDefaults = UserDefaults.standard
    var _players = 0
    var _powerup = 0
    var _speedup = 0
    var _isValidStage3 = false
    
    var players: Int {
        get {
            return _players
        }
        
        set(players) {
            _players = players
            userDefaults.set(players, forKey: userDefaultsKey.players.rawValue)
            userDefaults.synchronize()
        }
    }
    
    var powerup: Int {
        get {
            return _powerup
        }
        
        set(powerup) {
            _powerup = powerup
            userDefaults.set(powerup, forKey: userDefaultsKey.powerup.rawValue)
            userDefaults.synchronize()
        }
    }
    
    var speedup: Int {
        get {
            return _speedup
        }
        
        set(speedup) {
            _speedup = speedup
            userDefaults.set(speedup, forKey: userDefaultsKey.speedup.rawValue)
            userDefaults.synchronize()
        }
    }
    
    var isValidStage3: Bool {
        get {
            return _isValidStage3
        }
        
        set(isValidStage3) {
            _isValidStage3 = isValidStage3
            userDefaults.set(isValidStage3, forKey: userDefaultsKey.stage3.rawValue)
            userDefaults.synchronize()
        }
    }
    
    override init() {
        super.init()
        
        _players = userDefaults.integer(forKey: userDefaultsKey.players.rawValue)
        _powerup = userDefaults.integer(forKey: userDefaultsKey.powerup.rawValue)
        _speedup = userDefaults.integer(forKey: userDefaultsKey.speedup.rawValue)
        _isValidStage3 = userDefaults.bool(forKey: userDefaultsKey.stage3.rawValue)
        
        
        // PaymentManagerが発行する購入処理が完了した時に通知を受け取るように登録する
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.paymentCompletedNotification(notification:)),
                                               name: NSNotification.Name(rawValue: PaymentManager.paymentCompletedNotification),
                                               object: nil)
    }
    
    func bought(_ productId: String) {
        
        // 購入されたものをNSUserDefaultsで管理する
        // （実際にはsetterの中でNSUserDefaultsに保存している）
        switch productId {
        case "jp.co.shoeisya.Chapter8.players":
            players += 1
            break
        case "jp.co.shoeisya.Chapter8.powerup":
            powerup += 1
            break
        case "jp.co.shoeisya.Chapter8.speedup":
            speedup += 1
            break
        case "jp.co.shoeisya.Chapter8.stage3":
            isValidStage3 = true
            break
        default:
            break
        }
    }
    
    func productIds() -> Set<String> {
        return Set(arrayLiteral: "jp.co.shoeisya.Chapter8.players",
                   "jp.co.shoeisya.Chapter8.powerup",
                   "jp.co.shoeisya.Chapter8.speedup",
                   "jp.co.shoeisya.Chapter8.stage3")
    }
    
    // Mark: - PaymentNotification Method
    func paymentCompletedNotification(notification: Notification) {
        if let paymentTransaction = notification.object as? SKPaymentTransaction {
            bought(paymentTransaction.payment.productIdentifier)
        }
    }
}
