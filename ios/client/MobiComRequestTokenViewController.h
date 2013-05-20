//
//  MobiComRequestTokenViewController.h
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MobiComRequestTokenViewController : UIViewController<UITextFieldDelegate>

- (IBAction)onRequestTokenClick:(id)sender;

@property (nonatomic, retain) IBOutlet UITextField* tokenEndpointField;
@property (nonatomic, retain) IBOutlet UITextField* appIdField;
@property (nonatomic, retain) IBOutlet UITextField* appKeyField;
@property (nonatomic, retain) IBOutlet UITextField* authCodeField;
@property (nonatomic, retain) IBOutlet UITextField* redirectUrlField;
@property (nonatomic, retain) IBOutlet UIButton* requestTokenButton;

@end
