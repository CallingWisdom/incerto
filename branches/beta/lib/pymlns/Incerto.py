import sys


#Inference: infer mln database results query mode debug
#Learn: learnwts mln database mlnout mode 
if __name__ == '__main__':
	#sys.argv = [sys.argv[0], "infer", "smokers.mln", "smokers.db", "smokers.res",'cancer', "debug"]
	#sys.argv = [sys.argv[0], "learnwts", "smokers.mln", "smokers.db", "smokers-out.mln", "BPLL"]	
    	args = sys.argv[1:]
    	print args
    	#sys.path.append('/home/pedro/Desktop/pymlns')
    	import MLN
    	
    	#Reflexoes: Pode-se deixar assim e distribuir o pymlns a mesma... utilizadores podem dizer qual o pymlns que querem, mas isto traz um por defeito (pode-se fazer o mesmo com o alchemy)
    	#Se calhar faz mais sentido o python ler o config e nao o java...
    	
    	if args[0] == "infer":
    		mln = MLN.MLN(args[1])
    		evidence = MLN.evidence2conjunction(mln.combineDB(args[2]))
    		infargs = {"shortOutput":True, "outFile":file(args[3], "w") }
    		if len(args)>6 and args[6] == "debug":
    			infargs.update({"details":True, "verbose":True, "debugLevel":1})

		
		queries = args[4].split(";");
		if args[5] == "Exact":		#Mode = Exact, Gibbs, MC-SAT
			mln.inferExact(queries, evidence, **infargs)
		elif args[5] == "Gibbs":
			mln.inferGibbs(queries, evidence, **infargs)
		else:
			mln.inferMCSAT(queries, evidence, **infargs)

    		#mln.infer(queries, evidence, **infargs)
		print "Inference done!\n"
        	
        elif args[0] == "learnwts":
        	mln = MLN.MLN(args[1])
        	mln.combineDB(args[2])
        	lwtsargs = {"initialWts":False}
        	mln.learnwts(mode=args[4],**lwtsargs)		#Mode= PLL, LL, BPLL
        	mln.write(file(args[3], "w"))
    		print "Weight Learning done!\n"
    	else:
    		print "\nWrong parameters\n"
    	

