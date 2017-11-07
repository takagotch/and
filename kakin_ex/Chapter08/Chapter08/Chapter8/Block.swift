//
//  Block.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation

class Block {
    var x: Float
    var y: Float
    var width: Float
    var height: Float
    var status: Bool
    
    init(x: Float, y: Float, width: Float, height: Float, status: Bool) {
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.status = status
    }
    
    func setCoordinate(x: Float, y: Float) {
        self.x = x
        self.y = y
    }
}
