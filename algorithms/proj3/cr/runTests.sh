for i in {901..908};do java Main < cr/$i.in>output.txt;diff output.txt cr/$i.out; done
