//
//  BlockTable.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import Foundation

class BlockTable {
    static let blockColNum = 5
    static let blockRowNum = 3
    
    var blocks:[Block] = []
    var availableBlockNum: Int
    
    init(pattern: [Bool], width: Float, height: Float) {
        availableBlockNum = 0
        
        // ブロック1つ分の横幅、高さを計算する
        let blockWidth = width / (Float(BlockTable.blockColNum) + 2)
        let blockHeight = height / 15
        
        // ブロックを生成する
        for i in 0..<BlockTable.blockRowNum {
            for j in 0..<BlockTable.blockColNum {
                let status = pattern[i * BlockTable.blockColNum + j]
                
                let block = Block.init(x: blockWidth + Float(j) * blockWidth,
                                       y: blockHeight + Float(i) * blockHeight,
                                       width: blockWidth,
                                       height: blockHeight,
                                       status: status)
                blocks.append(block)
                
                if status {
                    // ブロックテーブル上に表示されるブロック数を数える
                    availableBlockNum += 1
                }
            }
        }
    }
    
    func setBall(_ ball:Ball) {
        blocks.forEach{ (block) in
            if block.status == false {
                return
            }
            
            // ボールがブロックの底辺に当たった場合
            if (block.x < ball.x - ball.radius && ball.x + ball.radius < block.x + block.width) &&
                (block.y + block.height / 2.0 < ball.y - ball.radius && ball.y - ball.radius < block.y + block.height) {
                // ボールのY軸進行方向を反転する
                ball.changeDirectionY()
                
                // ブロックを非表示にする
                block.status = false
                
                // ブロックの有効数を1つ減らす
                availableBlockNum -= 1
            }
                // ボールがブロックの上辺に当たった場合
            else if (block.x < ball.x - ball.radius && ball.x + ball.radius < block.x + block.width) &&
                (block.y < ball.y + ball.radius && ball.y + ball.radius < block.y + block.height / 2.0) {
                // ボールのY軸進行方向を反転する
                ball.changeDirectionX()
                
                // ブロックを非表示にする
                block.status = false
                
                // ブロックの有効数を1つ減らす
                availableBlockNum -= 1
            }
                // ボールがブロックの右辺に当たった場合
            else if (block.x + block.width - 10 < ball.x - ball.radius && ball.x - ball.radius < block.x + block.width) &&
                (block.y < ball.y - ball.radius && ball.y + ball.radius < block.y + block.height) {
                // ボールのX軸進行方向を反転する
                ball.changeDirectionX()
                
                // ブロックを非表示にする
                block.status = false
                
                // ブロックの有効数を1つ減らす
                availableBlockNum -= 1
            }
                // ボールがブロックの左辺に当たった場合
            else if (block.x < ball.x + ball.radius && ball.x + ball.radius < block.x + 10) &&
                (block.y < ball.y - ball.radius && ball.y + ball.radius < block.y + block.height) {
                // ボールのX軸進行方向を反転する
                ball.changeDirectionX()
                
                // ブロックを非表示にする
                block.status = false
                
                // ブロックの有効数を1つ減らす
                availableBlockNum -= 1
            }
        }
    }
}
