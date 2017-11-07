//
//  ViewController.swift
//  AdmobSample
//
//  Copyright © 2016年 shoeisha. All rights reserved.
//

import UIKit
import GoogleMobileAds

class ViewController: UIViewController {

    @IBOutlet weak var bannerView: GADBannerView!
    var interstitial: GADInterstitial!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // バナー広告
        bannerView.adUnitID = "ca-app-pub-3940256099942544/2934735716"
        bannerView.rootViewController = self
        bannerView.delegate = self
        
        let request = GADRequest()
        request.testDevices = [kGADSimulatorID]
        bannerView.load(request)
        
        // インタースティシャル広告
        createAndLoadInterstitial()
        
        Timer.scheduledTimer(withTimeInterval: 10, repeats: false) {
            timer in
            if self.interstitial.isReady {
                self.interstitial.present(fromRootViewController: self)
            } else {
                print("not ready")
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    fileprivate func createAndLoadInterstitial() {
        interstitial = GADInterstitial(adUnitID: "ca-app-pub-3940256099942544/4411468910")
        let request = GADRequest()
        request.testDevices = [kGADSimulatorID]
        interstitial.load(request)
    }
}

extension ViewController: GADBannerViewDelegate {
    public func adViewDidReceiveAd(_ bannerView: GADBannerView) {
        print(#function)
    }
    
    public func adView(_ bannerView: GADBannerView, didFailToReceiveAdWithError error: GADRequestError) {
        print(#function)
    }
    
    public func adViewWillPresentScreen(_ bannerView: GADBannerView) {
        print(#function)
    }
    
    public func adViewWillDismissScreen(_ bannerView: GADBannerView) {
        print(#function)
    }
    
    public func adViewDidDismissScreen(_ bannerView: GADBannerView) {
        print(#function)
    }
    
    public func adViewWillLeaveApplication(_ bannerView: GADBannerView) {
        print(#function)
    }

}

