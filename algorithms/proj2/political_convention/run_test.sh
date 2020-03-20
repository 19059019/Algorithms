for i in {1..7}; do  java -Xmx16m Main < pc/90$i.in > output.txt; diff output.txt  pc/90$i.out;echo $i; done
