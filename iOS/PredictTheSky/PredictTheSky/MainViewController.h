//
//  MainViewController.h
//  PredictTheSky
//
//  Created by Nick Charlton on 21/04/2012.
//  Copyright (c) 2012 Nick Charlton. MIT Licensed.
//

#import <UIKit/UIKit.h>
#import "AboutViewController.h"
#import "CoreLocation/CoreLocation.h"
#import "AFNetworking.h"

@interface MainViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, AboutViewControllerDelegate, CLLocationManagerDelegate>

@property (strong) CLLocationManager *locationManager;
@property (weak) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UILabel *nextEvent;
@property (weak, nonatomic) IBOutlet UILabel *nextEventObject;
@property (weak, nonatomic) IBOutlet UILabel *nextEventViewPeriod;
@property (weak, nonatomic) IBOutlet UILabel *nextEventConditions;
@property (weak, nonatomic) IBOutlet UILabel *otherEvents;

- (void)startMonitoringLocationUpdates;
- (void)fetchNextClearSkyEventWithLocation:(CLLocation *)location;
- (void)fetchNextSkyEventsWithLocation:(CLLocation *)location;

@end
