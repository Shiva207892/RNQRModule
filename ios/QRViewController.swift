//
//  QRViewController.swift
//  AwesomeProject
//
//  Created by Shiva on 02/02/23.
//

import UIKit
import BarCodeScanner
import AVFoundation

class QRViewController: UIViewController {
  
  @objc var width: NSNumber = 50;

    @IBOutlet var qrScannerContainerView: UIView!
    let qrScannerView = BarCodeScannerView()

    override func viewDidLoad() {
      print("view did load start")
        super.viewDidLoad()
        // Do any additional setup after loading the view.
      setupQRScannerView()
    }

    private func setupQRScannerView() {
      print("setup qr scanner view")
        qrScannerView.frame = self.view.bounds
        self.view.addSubview(qrScannerView)
        qrScannerView.configure(delegate: self, input: .init(isBlurEffectEnabled: false))
        qrScannerView.startRunning()
    }
  
    override func viewDidAppear(_ animated: Bool) {
      print("view did appear")
        super.viewDidAppear(animated)
//        setupQRScannerView()
    }
  
    override func viewDidDisappear(_ animated: Bool) {
      print("print view did disappear")
        super.viewDidDisappear(animated)
        qrScannerView.stopRunning()
    }
  
    private func showPermissionAlert() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { [weak self] in
            let alert = UIAlertController(title: "Error", message: "Camera is required to use in this application", preferredStyle: .alert)
            alert.addAction(.init(title: "OK", style: .default))
            self?.present(alert, animated: true)
        }
    }
  
    private func showSessionErrorAlert() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { [weak self] in
            let alert = UIAlertController(title: "Error", message: "Some error occured while starting session", preferredStyle: .alert)
            alert.addAction(.init(title: "OK", style: .default))
            self?.present(alert, animated: true)
        }
    }
  
  func showAlert(code: String) {
//        let alertController = UIAlertController(title: code, message: nil, preferredStyle: .actionSheet)
//        let copyAction = UIAlertAction(title: "Copy", style: .default) { [weak self] _ in
//            UIPasteboard.general.string = code
//            self?.qrScannerView.rescan()
//        }
//        alertController.addAction(copyAction)
//        let searchWebAction = UIAlertAction(title: "Search Web", style: .default) { [weak self] _ in
//            UIApplication.shared.open(URL(string: "https://www.google.com/search?q=\(code)")!, options: [:], completionHandler: nil)
//            self?.qrScannerView.rescan()
//        }
//        alertController.addAction(searchWebAction)
//        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: { [weak self] _ in
//            self?.qrScannerView.rescan()
//        })
//        alertController.addAction(cancelAction)
//        present(alertController, animated: true, completion: nil)
      
      let alert = UIAlertController(title: "Result", message: code, preferredStyle: UIAlertController.Style.alert)

              // add an action (button)
//                alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
      let cancelAction = UIAlertAction(title: "Ok", style: .cancel, handler: { [weak self] _ in
          self?.qrScannerView.rescan()
      })
      alert.addAction(cancelAction)

              // show the alert
              self.present(alert, animated: true, completion: nil)
          }
  
  
//    func showAlert(code: String) {
//      print("show alert called");
//        let alertController = UIAlertController(title: code, message: nil, preferredStyle: .actionSheet)
//        let copyAction = UIAlertAction(title: "Copy", style: .default) { [weak self] _ in
//            UIPasteboard.general.string = code
//            self?.qrScannerView.rescan()
//        }
//        alertController.addAction(copyAction)
//        let searchWebAction = UIAlertAction(title: "Search Web", style: .default) { [weak self] _ in
//            UIApplication.shared.open(URL(string: "https://www.google.com/search?q=\(code)")!, options: [:], completionHandler: nil)
//            self?.qrScannerView.rescan()
//        }
//        alertController.addAction(searchWebAction)
//        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: { [weak self] _ in
//            self?.qrScannerView.rescan()
//        })
//        alertController.addAction(cancelAction)
//        present(alertController, animated: true, completion: nil)
//    }
}

extension QRViewController: BarCodeScannerViewDelegate {
  
  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, authorizationDidFailure error: BarCodeScanner.BarCodeScannerError)
  {
      self.showPermissionAlert()
  }

  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, didFailure error: BarCodeScanner.BarCodeScannerError)
  {
      self.showSessionErrorAlert()
  }

  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, didSuccess code: String, barCodeType: AVMetadataObject.ObjectType)
  {
    self.qrScannerView.rescan()
      print("debug didSuccess is: \(code)")
      print("debug barCodeType: barCodeType = \(barCodeType)")
    self.showAlert(code: code)
  }
    
//    func barCodeScannerView(_ codeScannerView: BarCodeScannerView, authorizationDidFailure error: BarCodeScannerError)
//    {
//        self.showPermissionAlert()
//    }
//    func barCodeScannerView(_ codeScannerView: BarCodeScannerView, didFailure error: BarCodeScannerError)
//    {
//        self.showSessionErrorAlert()
//    }
//    func barCodeScannerView(_ codeScannerView: BarCodeScannerView, didSuccess code: String, barCodeType:AVMetadataObject.ObjectType)
//    {
//        self.showAlert(code: code)
//        print("debug barCodeType: barCodeType = \(barCodeType)")
//    }
}
