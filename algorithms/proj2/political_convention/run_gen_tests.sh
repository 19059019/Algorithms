for i in {1..1000}; do  java -Xmx16m Main < justin/test$i.in > output.txt; diff output.txt justin/test$i.out;echo $i; done
