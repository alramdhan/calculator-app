//
//  AppDelegate.swift
//  iosApp
//
//  Created by SKK Staf on 20/05/25.
//

import Foundation
import SwiftUI
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        PreferencesFactory().setInstance(preferences: IOSPreferences())
        return true
    }
}
