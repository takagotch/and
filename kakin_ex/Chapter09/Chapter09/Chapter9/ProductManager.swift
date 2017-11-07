//
//  ProductManager.swift
//  Chapter9
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation
import StoreKit

class ProductManager:NSObject {
    enum userDefaultsKey : String {
        case content1ExpiresDate = "Content1ExpiresDate"
        case content2ExpiresDate = "Content2ExpiresDate"
    }
    
    // シングルとして扱う
    static let sharedInstance = ProductManager()
    
    let userDefaults = UserDefaults.standard
    
    override init() {
        super.init()
    }
    
    func isContent1Enable() -> Bool {
        let currentTime =  Date().timeIntervalSince1970
        let content1ExpiresData = userDefaults.integer(forKey: userDefaultsKey.content1ExpiresDate.rawValue)
        
        if currentTime < Double(content1ExpiresData) {
            return true
        } else {
            return false
        }
    }

    func isContent2Enable() -> Bool {
        let currentTime = Date.timeIntervalBetween1970AndReferenceDate
        let content2ExpiresData = userDefaults.integer(forKey: userDefaultsKey.content2ExpiresDate.rawValue)
        
        if currentTime < Double(content2ExpiresData) {
            return true
        } else {
            return false
        }
    }
    
    func productIds() -> Set<String> {
        return Set(arrayLiteral: "jp.co.shoeisya.Chapter9.content1.7days",
                   "jp.co.shoeisya.Chapter9.content1.1month",
                   "jp.co.shoeisya.Chapter9.content2.7days",
                   "jp.co.shoeisya.Chapter9.content2.1month")
    }
    
    func getProductName(_ productId: String) -> String? {
        var name:String?
        
        switch productId {
        case "jp.co.shoeisya.Chapter9.content1.7days":
            name = "Content 1 期間:1週間"
        case "jp.co.shoeisya.Chapter9.content1.1month":
            name = "Content 1 期間:1ヶ月"
        case "jp.co.shoeisya.Chapter9.content2.7days":
            name = "Content 2 期間:1週間"
        case "jp.co.shoeisya.Chapter9.content2.1month":
            name = "Content 2 期間:1ヶ月"
        default:
            break
        }
        
        return name
    }
    
    func saveExpiresDate(_ content1:UInt64, content2:UInt64) {
        // msをsecに直すために1000で割る
        if content1 > 0 {
            userDefaults.set(content1 / 1000, forKey: userDefaultsKey.content1ExpiresDate.rawValue)
        }

        if content2 > 0 {
            userDefaults.set(content2 / 1000, forKey: userDefaultsKey.content2ExpiresDate.rawValue)
        }
        
    }
}
