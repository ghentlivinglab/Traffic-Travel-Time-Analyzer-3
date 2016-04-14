use DBI;

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

foreach $index (1..$#uri) {
	print "$index: $uri[$index]\n";
}

$sth->finish();

# Disconnect from the database
$dbh->disconnect();