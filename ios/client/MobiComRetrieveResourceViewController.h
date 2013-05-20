//
//  MobiComRetrieveResourceViewController.h
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MobiComRetrieveResourceViewController : UIViewController<UITextFieldDelegate>

- (IBAction)onRetrieveResourceClick:(id)sender;

@property (nonatomic, retain) IBOutlet UITextField* accessTokenField;
@property (nonatomic, retain) IBOutlet UITextField* refreshTokenField;
@property (nonatomic, retain) IBOutlet UITextField* expiresInField;
@property (nonatomic, retain) IBOutlet UITextField* resourceEndpointField;
@property (nonatomic, retain) IBOutlet UITextField* scopeField;
@property (nonatomic, retain) IBOutlet UIButton* retrieveResourceButton;

@end
