#!/opt/local/bin/perl
use Spreadsheet::ParseExcel;

$file="/Users/dave/documents/work/nick/Production Allocation_20110731_NICK.xlsx"


my $xls = Spreadsheet::ParseExcel::Workbook->Parse($file) or print_error($!);
foreach my $workbook(@{$xls->{Worksheet}})
{
  my $csv = $home.'/'.$workbook.'.csv';
  print "CSV: $csv\n";
#  open(W_CSV,">$csv") or print_error($csv." ".$!);
#  for(my $row = $workbook->{MinRow}; defined $workbook->{MaxRow} && $row <= $workbook->{MaxRow}; $row++){
#    my @values = ();
#    for(my $col = $workbook->{MinCol}; defined $workbook->{MaxCol} && $col <= $workbook->{MaxCol}; $col++){
#      my $cell  = $workbook->{Cells}[$row][$col];
#      my $val   = $cell ? $cell->Value : '';
#      push(@values, $val);
#    }
#    print W_CSV join(';',@values),"\n";
  }
  close W_CSV;
}

sub print_error{
  print STDERR "Error: ",shift,"\n";
  if(shift eq 'use'){
    print qq~
      Usage: $0 <excel_file> <output_path>

~;
  }
  exit -1;
}

