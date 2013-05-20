//
//  MobiComViewController.h
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MobiComViewController : UIViewController<UITextFieldDelegate>

- (IBAction)onRetrieveCodeClick:(id)sender;

@property (nonatomic, retain) IBOutlet UITextField* appIdField;
@property (nonatomic, retain) IBOutlet UITextField* appKeyField;
@property (nonatomic, retain) IBOutlet UITextField* authUrlField;
@property (nonatomic, retain) IBOutlet UITextField* redirectUrlField;
@property (nonatomic, retain) IBOutlet UITextField* scopeField;
@property (nonatomic, retain) IBOutlet UIButton* retrieveCodeButton;

@end
