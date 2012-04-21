//
//  MainViewController.h
//  PredictTheSky
//
//  Created by Nick Charlton on 21/04/2012.
//  Copyright (c) 2012 Nick Charlton. MIT Licensed.
//

#import <UIKit/UIKit.h>
#import "AboutViewController.h"

@interface MainViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, AboutViewControllerDelegate>

@property (weak) IBOutlet UITableView *tableView;

@end
