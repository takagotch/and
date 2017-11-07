//
//  BreakoutView.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import UIKit

protocol BreakoutViewProtocol {
    func clearGame()
    func lostBall()
}

class BreakoutView: UIView {
    let ballDx: Float = 5.0
    let ballDy: Float = 5.0
    let gameOverBorderLine: Float = 10.0
    
    let racketWidth: Float = 100.0
    let racketHeight: Float = 20.0
    let racketInitPosY: Float = 30.0
    
    let ballInitPosY: Float = -10.0
    
    var width: Float = 0
    var height: Float = 0

    var delegate: BreakoutViewProtocol?
    var isValidGame = false
    var pattern: [Bool] = []
    var ball: Ball!
    var racket: Racket!
    var blockTable: BlockTable!

    var timer: Timer?

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.timer = Timer.scheduledTimer(timeInterval: 0.1,
                                          target: self,
                                          selector: #selector(BreakoutView.timerCallback(_:)),
                                          userInfo: nil,
                                          repeats: true)
    }
    
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.timer = Timer.scheduledTimer(timeInterval: 0.1,
                                          target: self,
                                          selector: #selector(BreakoutView.timerCallback(_:)),
                                          userInfo: nil,
                                          repeats: true)
    }
    
    func timerCallback(_ timer: Timer) {
        DispatchQueue.main.async {
            self.setNeedsDisplay()
            self.setNeedsLayout()
            self.layoutIfNeeded()
        }
    }
    override func draw(_ rect: CGRect) {
        
        if ball.isActive {
            if ball.x - ball.radius <= 0 {
                ball.directionX = .right;
            } else if ball.x + ball.radius >= width {
                ball.directionX = .left;
            }
            
            if (ball.directionX == .right) {
                ball.moveX(ballDx)
            } else if (ball.directionX == .left) {
                ball.moveX(-ballDx)
            }
            
            if ball.y - ball.radius <= 0 {
                ball.directionY = .bottom;
            }
            
            if ball.directionY == .bottom {
                ball.moveY(ballDy)
            } else if ball.directionY == .top {
                ball.moveY(-ballDy)
            }
            
            if ((racket.x < ball.x + ball.radius) && (ball.x + ball.radius < racket.x + racket.width)) &&
                ((racket.y < ball.y + ball.radius) && (ball.y + ball.radius <  racket.y + racket.height / 2)) {
                ball.directionY = .top;
            } else if ball.y > racket.y + gameOverBorderLine {
                ball.isActive = false
                delegate?.lostBall()
            }
            
            blockTable.setBall(ball)
            
            if blockTable.availableBlockNum == 0 {
                ball.isActive = false
                delegate?.clearGame()
            }
            
            drawBall()
        }
        
        drawRacket()
        drawBlocks()
    }
    
    func drawBall() {
        if let context = UIGraphicsGetCurrentContext() {
            context.setFillColor(red: 0.0, green: 0.0, blue: 1.0, alpha: 1.0);
            context.fillEllipse(in: CGRect(x: CGFloat(ball.x - ball.radius),
                                           y: CGFloat(ball.y - ball.radius),
                                           width: CGFloat(ball.radius * 2.0),
                                           height: CGFloat(ball.radius * 2.0)))
        }
    }
    
    
    func drawRacket() {
        if let context = UIGraphicsGetCurrentContext() {
            context.setFillColor(red: 0.0, green: 0.0, blue: 0.5, alpha: 1.0);
            context.fill(CGRect(x: CGFloat(racket.x),
                                y: CGFloat(racket.y),
                                width: CGFloat(racket.width),
                                height: CGFloat(racket.height)))
        }
    }
    
    func drawBlocks() {
        if let context = UIGraphicsGetCurrentContext() {
            context.setFillColor(red: 0.0, green: 0.0, blue: 1.0, alpha: 1.0);
            
            for block in blockTable.blocks.filter({$0.status}) {
                context.fill(CGRect(x: CGFloat(block.x),
                                    y: CGFloat(block.y),
                                    width: CGFloat(block.width),
                                    height:CGFloat(block.height)));
                
            }
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if !ball.isActive && isValidGame {
            ball.isActive = true
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let point = touches.first?.location(in: self) {
            racket.x = Float(point.x) - racket.width
        }
        
    }
    
    func initGame() {
        isValidGame = true
        width = Float(frame.width)
        height = Float(frame.height)
        
        racket = Racket.init(x: width / 2.0 - racketWidth / 2.0,
                             y: height - racketInitPosY - racketHeight,
                             width: racketWidth,
                             height: racketHeight)
        ball = Ball.init(x: racket.x + racketWidth / 2.0,
                         y: racket.y - racket.height)
        blockTable = BlockTable.init(pattern: pattern,
                                     width: width,
                                     height: height)
    }
    
    func resetBallAndRacket() {
        racket = Racket.init(x: width / 2.0 - racketWidth / 2.0,
                             y: height - racketInitPosY - racketHeight,
                             width: racketWidth,
                             height: racketHeight)
        ball = Ball.init(x: racket.x + racketWidth / 2.0,
                         y: racket.y - racket.height)
    }
    
    func poweup() {
        racket.powerup()
    }
    
    func speedup() {
        ball.speed *= 1.3
    }
}
