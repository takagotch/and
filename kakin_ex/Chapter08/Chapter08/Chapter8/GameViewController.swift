//
//  GameViewController.swift
//  Chapter8
//
//  Copyright © 2016年 shoeisya. All rights reserved.
//

import UIKit

class GameViewController: UIViewController {
    
    @IBOutlet weak var playersLabel: UILabel!
    @IBOutlet weak var speedupLabel: UILabel!
    @IBOutlet weak var powerupLabel: UILabel!
    @IBOutlet weak var breakoutView: BreakoutView!
    @IBOutlet weak var playersButton: UIButton!
    @IBOutlet weak var speedupButton: UIButton!
    @IBOutlet weak var powerupButton: UIButton!
    
    var pattern: [Bool] = []
    var players: Int = 0
    var manager: ProductManager!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        manager = ProductManager.sharedInstance

        players = 2
        breakoutView.pattern = pattern
        breakoutView.delegate = self
        breakoutView.initGame()
        
        updateLabel()
        updateButton()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
    
    @IBAction func handlePlayersButton(_ sender: Any) {
        manager.players -= 1
        players += 1
        
        updateLabel()
        updateButton()
    }
    
    @IBAction func handleSpeedupButton(_ sender: Any) {
        manager.speedup -= 1
        breakoutView.speedup()
        updateLabel()
        updateButton()
    }
    @IBAction func handlePowerupButton(_ sender: Any) {
        manager.powerup -= 1
        breakoutView.poweup()
        updateLabel()
        updateButton()
    }
    
    func updateLabel() {
        playersLabel.text = "PLAYERS:\(players)"
        speedupLabel.text = "SPEED UP:\(manager.speedup)"
        powerupLabel.text = "POWER UP:\(manager.powerup)"
    }
    
    func updateButton() {
        playersButton.isEnabled = manager.players > 0 ? true : false
        speedupButton.isEnabled = manager.speedup > 0 ? true : false
        powerupButton.isEnabled = manager.powerup > 0 ? true : false
    }
}

extension GameViewController: BreakoutViewProtocol {
    func clearGame() {
        print(#function)
        dismiss(animated: true, completion: nil)
    }
    
    func lostBall() {
        print(#function)
        breakoutView.resetBallAndRacket()
        players -= 1
        
        if players > 0 {
            updateLabel()
        } else {
            dismiss(animated: true, completion: nil)
        }
      
    }
}
