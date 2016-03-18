use URI;
use Web::Scraper;
use WWW::Selenium;
use Encode;
use strict;
use DBI;

# https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13&from_lat=51.03687&from_lon=3.7101&to_lat=51.03109&to_lon=3.70653&at_req=0&at_text=Now

my $dbh = DBI->connect('dbi:mysql:database=vop;host=10.20.0.2:3306','vop','vop',{AutoCommit=>1,RaiseError=>1,PrintError=>0});

# now retrieve data from the table.
my $sth = $dbh->prepare("SELECT * FROM trajecten");
$sth->execute();

my @uri;
while (my $ref = $sth->fetchrow_hashref()) {
	$uri[$ref->{'id'}] = "https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13" .
				"&from_lat=$ref->{'start_latitude'}" .
				"&from_lon=$ref->{'start_longitude'}" .
				"&to_lat=$ref->{'end_latitude'}" .
				"&to_lon=$ref->{'end_longitude'}" .
				"&at_req=0&at_text=Now";
}

$sth->finish();

# Disconnect from the database
$dbh->disconnect();

# Selenium for javascript added html
my $sel = WWW::Selenium->new( host => "169.254.65.215",
								port => 5555,
								browser => "*firefox",
								browser_url => "http://www.google.be"
							);

$sel->start;
$sel->open(URI->new($uri));
$sel->wait_for_page_to_load(3000);

# Scraper blok aanmaken
my $route = scraper {
	# In welk blok gekeken moet worden
	process ".route-info.route_0", "routes[]" => scraper {
		# Haal info op van elementen uit het blok
		process ".route-name", name => 'TEXT';
		process ".route-stats .route-length", length => 'TEXT';
		process ".route-stats .route-time", time => 'TEXT';
	};
};

# Route scrapen
my $res = $route->scrape( $sel->get_html_source() );

$sel->stop;

# Overloop de routes
for my $route (@{$res->{routes}}) {
	print Encode::encode("utf8", "$route->{name}\n");
	print Encode::encode("utf8", "$route->{length}\n");
	print Encode::encode("utf8", "$route->{time}\n");
}