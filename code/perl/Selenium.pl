use URI;
use Web::Scraper; # Install
use WWW::Selenium; # Install
use Encode;
use strict;
use DBI; # Install

my $dbh = DBI->connect('dbi:mysql:database=vop;host=10.20.0.2:3306','vop','vop',{AutoCommit=>1,RaiseError=>1,PrintError=>0});

# Retrieve trajecten data
my $sth = $dbh->prepare("SELECT * FROM trajecten");
$sth->execute();

# Store routes as valid waze uri in hash
my %uri;
while (my $ref = $sth->fetchrow_hashref()) {
    $uri{$ref->{'id'}} = "https://www.waze.com/livemap?ll=13&lat=51.05175&lon=3.72711&navigate=yes&zoom=13" .
                "&from_lat=$ref->{'start_latitude'}" .
                "&from_lon=$ref->{'start_longitude'}" .
                "&to_lat=$ref->{'end_latitude'}" .
                "&to_lon=$ref->{'end_longitude'}" .
                "&at_req=0&at_text=Now";
}

$sth->finish();

# Selenium for javascript added html
my $sel = WWW::Selenium->new( host => "192.168.0.100",
								port => 5555,
								browser => "*firefox",
								browser_url => "http://www.google.be"
							);

$sel->start;

# Scraper blok aanmaken
my $routes = scraper {
	# In welk blok gekeken moet worden
	#process ".route-info.route_0", "routes[]" => scraper {
	#    # Haal info op van elementen uit het blok
	#    process ".route-name", name => 'TEXT';
	#    process ".route-stats .route-length", length => 'TEXT';
	#    process ".route-stats .route-time", time => 'TEXT';
	#};

	# In welk blok gekeken moet worden
	process ".route-info.route_0", "routes[]" => scraper {
	    # Haal info op van elementen uit het blok
	    process ".route-name", name => 'TEXT';
	    process ".route-stats .route-length", length => 'TEXT';
	    process ".route-stats .route-time", time => 'TEXT';
	};
};

foreach my $key (keys %uri) {
	my $value = $uri{$key};

	$sel->open(URI->new($value));
	$sel->refresh();
	$sel->wait_for_page_to_load(9000);
	
	# Route scrapen
	my $res = $routes->scrape( $sel->get_html_source() );

	for my $route (@{$res->{routes}}) {
		print Encode::encode("utf8", "$route->{name}\n");
		$sth = $dbh->prepare(qq{
			INSERT INTO metingen (reistijd, traject_id, provider_id) VALUES (?, ?, ?)
		});
		my $time = $route->{time};
		$time =~ s/\D//g;
		if($time == "0") {
			$time = "60";
		} else {
			$time = $time * 60;
		}
		$sth->execute($time, $key, 7);
	}
}

# Close selenium and db connection
$sel->stop;
$dbh->disconnect();