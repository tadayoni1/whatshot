import sys, getopt, populartimes, json

def main(argv):

  if( len(sys.argv) != 7):
    print ('usage: test.py api venue-type x1 y1 x2 y2')
    sys.exit(2)

  api = sys.argv[1];
  venueType = sys.argv[2];

  x1 = float(sys.argv[3]);
  y1 = float(sys.argv[4]);

  x2 = float(sys.argv[5]);
  y2 = float(sys.argv[6]);

  res = populartimes.get(api, [venueType],(x1, y1), (x2, y2))

  resStr = json.dumps(res, ensure_ascii=False)

  f = open('/home/forge/default/public/populartimes-api/output', 'w')
  f.write(resStr)
  f.close()

  print (resStr)

if __name__ == "__main__":
   main(sys.argv[1:])