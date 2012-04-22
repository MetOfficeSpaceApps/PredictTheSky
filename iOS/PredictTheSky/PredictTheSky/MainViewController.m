//
//  MainViewController.m
//  PredictTheSky
//
//  Created by Nick Charlton on 21/04/2012.
//  Copyright (c) 2012 Nick Charlton. MIT Licensed.
//

#import "MainViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController

@synthesize tableView = _tableView;
@synthesize locationManager = _locationManager;

@synthesize nextEvent = _nextEvent;
@synthesize nextEventObject = _nextEventPlace;
@synthesize nextEventViewPeriod = _nextEventViewPeriod;
@synthesize nextEventConditions = _nextEventConditions;
@synthesize otherEvents = _otherEvents;

#pragma mark View Lifecycle
- (void)viewWillAppear:(BOOL)animated
{
    [self startMonitoringLocationUpdates];
}

#pragma mark UITableViewDataSource
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [self.tableView dequeueReusableCellWithIdentifier:@"EventCell"];
    
    /*
      Image: 1 (UIImage)
      Object: 2 (UILabel)
      View Period: 3 (UILabel)
      Conditions: 4 (UILabel)
    */
    
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 5;
}

#pragma mark AboutViewControllerDelegate
-(void)didCloseAboutViewController
{
    [self.navigationController dismissModalViewControllerAnimated:YES];
}

#pragma mark UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
}

#pragma mark Segue Handling
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqualToString:@"aboutSegue"]) {
        AboutViewController *aboutVC = (AboutViewController *)segue.destinationViewController;
        
        aboutVC.delegate = self;
    }
}

#pragma mark Location Services
- (void)startMonitoringLocationUpdates
{
    if (nil == self.locationManager)
        self.locationManager = [[CLLocationManager alloc] init];
    
    self.locationManager.delegate = self;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyKilometer;
    self.locationManager.distanceFilter = 500;
    
    [self.locationManager startUpdatingLocation];
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    #if DEBUG
    NSLog(@"New Location: %@", newLocation);
    #endif
    
    [self fetchNextClearSkyEventWithLocation:newLocation];
    [self fetchNextSkyEventsWithLocation:newLocation];
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    #if DEBUG
    NSLog(@"Failed with Error: %@", error);
    #endif
    
    static BOOL warning_shown;
    
    if (!warning_shown) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Locate" message:@"You need to enable Location Services to locate the device." delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
        
        warning_shown = YES;
        [alert show];
    }
}

#pragma mark Web Service Integration
- (void)fetchNextClearSkyEventWithLocation:(CLLocation *)location
{
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.adamretter.org.uk/spaceapps/space.xql?lat=%f&lng=%f&format=json&nextClear=true", location.coordinate.latitude, location.coordinate.longitude]];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        
        NSDictionary *json = (NSDictionary *)JSON;
        
        #ifdef DEBUG
        NSLog(@"Response: %@", JSON);
        #endif
        
        if ([json objectForKey:@"none"]) {
            #ifdef DEBUG
            NSLog(@"No clear sky events existed.");
            #endif
            return;
        }
        
    } failure:nil];
    
    [operation start];
}

- (void)fetchNextSkyEventsWithLocation:(CLLocation *)location
{
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"http://www.adamretter.org.uk/spaceapps/space.xql?lat=%f&lng=%f&format=json", location.coordinate.latitude, location.coordinate.longitude]];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        
        #ifdef DEBUG
        //NSLog(@"Response: %@", JSON);
        #endif
        
    } failure:nil];
    
    [operation start];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

@end
