//
//  Racket.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation

class Racket {
    public var x:Float
    public var y:Float
    var width:Float
    var height:Float
    
    init(x: Float, y: Float, width: Float, height: Float) {
        self.x = x
        self.y = y
        self.width = width
        self.height = height
    }
    
    func powerup() {
        width *= 1.3
    }
}
