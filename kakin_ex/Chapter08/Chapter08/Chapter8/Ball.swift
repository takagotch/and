//
//  Ball.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation

enum BallXDirection {
    case right
    case left
}

enum BallYDirection {
    case top
    case bottom
}

class Ball {
    var radius: Float
    var x: Float
    var y: Float
    var speed: Float
    var directionX: BallXDirection
    var directionY: BallYDirection
    var isActive: Bool
    
    init(x: Float, y: Float) {
        self.x = x
        self.y = y
        radius = 7.5
        speed = 1.0
        directionX = .right
        directionY = .top
        isActive = false
    }
    
    func moveX(_ dx:Float) {
        x += dx * speed
    }

    func moveY(_ dy:Float) {
        y += dy * speed
    }
    
    func reset(x: Float, y: Float) {
        self.x = x
        self.y = y
        directionX = .right
        directionY = .top
    }
    
    func changeDirectionX() {
        if directionX == .right {
            directionX = .left
        } else {
            directionX = .right
        }
    }
    
    func changeDirectionY() {
        if directionY == .top {
            directionY = .bottom
        } else {
            directionY = .top
        }
    }

}
