for i in {901..910};do echo "TEST $i:"; java Main < mf/$i.in>output.txt;diff output.txt mf/$i.out; done
