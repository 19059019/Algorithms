for i in {901..914};do java -Xmx16m Main < tn/$i.in>output.txt;diff output.txt tn/$i.out; done
