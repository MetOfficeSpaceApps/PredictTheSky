//
//  AboutViewController.h
//  PredictTheSky
//
//  Created by Nick Charlton on 21/04/2012.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol AboutViewControllerDelegate <NSObject>

-(void)didCloseAboutViewController;

@end

@interface AboutViewController : UIViewController

@property (strong) id<AboutViewControllerDelegate> delegate;

- (IBAction)doneButton:(id)sender;

@end
