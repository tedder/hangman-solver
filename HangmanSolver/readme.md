## Assumptions:

* I'm only maintaining state in the Solver. Since the HangmanGame is passed in, I should probably use it for state.
* Copied words.txt to net.inervo so the Dictionary can see it. It's not a production way to handle the dependency, I just wanted to get it working.
* I avoided researching algorithms that are used to solve similar problems. Generally it's good to not reinvent the world but it felt outside the spirit of the problem.
* getScore made it difficult to collect statistics, so the code avoids calling it.
* There's zero thought given to different alphabets. The solutions will likely fail in an unknown and spectacular manner.
* The one word-guessing algorithm (MostPopularOurDictionaryWithWords) has very poor performance (as a quick read would indicate). It's a proof-of-concept to show that word guessing is helpful.

## Next 'technical debt' steps:

* The various switches and levers are buried in weird places. For instance, Runner contains the list of solvers that are used, number of guesses allowed, and the number of words in the game, but SolutionStatistics has the hardcoded "probability of a solution by 5" number.
* words.txt isn't being loaded properly by Dictionary, so I copied it to the net.inervo.hangman directory.
* The .classpath contains a hardcoded directory path to commons-io on my personal development machine. I don't think there are any other local-machine dependencies.  
* More unit tests.
* jdoc-style documentation. Comments exist but I've made no attempt to document the code for outsiders.

## Next algorithmic steps:

* Other than brute force word matching, no effort has been taken to use existing information to choose the next letter. In other words, "given Q, suggest U".
* A corpus including word popularity would likely work well with human-chosen words.
* Research common/related algorithms.

## Changes to provided code:

* Added a package declaration to each class.
* Made HangmanGame and HangmanGame.Status public.


## Overview of algorithms

These are listed in order of increasing accuracy:

* *MostPopularOurDictionary*
                   games=1000  avg_wrong_guesses=10.180000 prob_below_5=0.083000

* *ReverseSequentialOrder* - guessing the letters in reverse order (z, y, x, w).
* *RandomOrder* - choosing letters at random. This idea came from the randomization used to optimize QuickSort.
* *SequentialOrder* - simply guessing the letters in order (a, b, c, d).
* *MostPopularCornell* - using a list of popular letters calculated from 40k words at Cornell. (e, t, a, o .. q, j, z)
* *MostPopularOxford* - a list of popular letters compiled from the Oxford Compact dictionary (e, t, a, o .. x, q, z).
* *MostPopularOurDictionary* - letter popularity using the words.txt corpus.
* *MostPopularOurDictionaryWithWords* - using letter popularity and naively guessing words based on the *set* of letters already guessed.
* *MostPopularOurDictionaryWithWordRegex* - using letter popularity and a simple matching regex to capture repeated letters and positional information. (likely faster than the naive implementation in the previous solution) 


## Sample output


### guesses=99

With the "number of guesses" set to 99. This captures all the information above but is different than the expected output to the problem. Note the avg_wrong_guesses data gives a much better indication of solution fitness.

```
net.inervo.hangman.solver.MostPopularOurDictionary                   games=1000  avg_wrong_guesses=10.180000 prob_below_5=0.083000
net.inervo.hangman.solver.RandomOrder                                games=1000  avg_wrong_guesses=16.380000 prob_below_5=0.001000
net.inervo.hangman.solver.MostPopularCornell                         games=1000  avg_wrong_guesses=11.005000 prob_below_5=0.055000
net.inervo.hangman.solver.MostPopularOxford                          games=1000  avg_wrong_guesses=10.996000 prob_below_5=0.054000
net.inervo.hangman.solver.SequentialOrder                            games=1000  avg_wrong_guesses=13.748000 prob_below_5=0.001000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWords          games=1000  avg_wrong_guesses=10.180000 prob_below_5=0.083000
net.inervo.hangman.solver.ReverseSequentialOrder                     games=1000  avg_wrong_guesses=17.277000 prob_below_5=0.001000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWordRegex      games=1000  avg_wrong_guesses=02.681000 prob_below_5=0.804000
```

### guesses=6

Output with "number of guesses" set to 6. This provides less information on the average number of guesses to provide a solution. (in fact, the avg_wrong_guesses is basically noise)

```
net.inervo.hangman.solver.MostPopularOurDictionary                   games=1000  avg_wrong_guesses=06.506000 prob_below_5=0.083000
net.inervo.hangman.solver.RandomOrder                                games=1000  avg_wrong_guesses=06.980000 prob_below_5=0.003000
net.inervo.hangman.solver.MostPopularCornell                         games=1000  avg_wrong_guesses=06.652000 prob_below_5=0.055000
net.inervo.hangman.solver.MostPopularOxford                          games=1000  avg_wrong_guesses=06.662000 prob_below_5=0.054000
net.inervo.hangman.solver.SequentialOrder                            games=1000  avg_wrong_guesses=06.989000 prob_below_5=0.001000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWords          games=1000  avg_wrong_guesses=06.506000 prob_below_5=0.083000
net.inervo.hangman.solver.ReverseSequentialOrder                     games=1000  avg_wrong_guesses=06.997000 prob_below_5=0.001000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWordRegex      games=1000  avg_wrong_guesses=02.357000 prob_below_5=0.804000
```

### Specific words given in problem:

The number of words that were determined successfully by these algorithms are *substantially* lower than the implied solutions in the problem email. Admittedly, those were guessed with a combination of letter+word guesses, where six of eight solutions given here are purely based on letter guessing.

```
net.inervo.hangman.solver.MostPopularOurDictionary                   games=15    avg_wrong_guesses=09.533333 prob_below_5=0.066667
net.inervo.hangman.solver.RandomOrder                                games=15    avg_wrong_guesses=15.466667 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularCornell                         games=15    avg_wrong_guesses=09.600000 prob_below_5=0.133333
net.inervo.hangman.solver.MostPopularOxford                          games=15    avg_wrong_guesses=09.800000 prob_below_5=0.133333
net.inervo.hangman.solver.SequentialOrder                            games=15    avg_wrong_guesses=13.666667 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWords          games=15    avg_wrong_guesses=09.533333 prob_below_5=0.066667
net.inervo.hangman.solver.ReverseSequentialOrder                     games=15    avg_wrong_guesses=17.266667 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularOurDictionaryWithWordRegex      games=15    avg_wrong_guesses=02.600000 prob_below_5=0.800000
```



## Output commentary

I struggled with improving accuracy (in terms of wrong guesses or probability of solving with under 5 mistakes) until I decided to give word guessing a try. The second solution, regex word guessing, gives performance that is about 10x better than anything I had tried.
 
Regex word guessing is embarrassingly accurate for how much time I put into other solutions. The word guessing algorithms are substantially slower, of course. I haven't checked the performance of the two word guessing algorithms. I suspect the regex version is faster. I also haven't optimized the word guessing algorithms for performance.

Aside from the word guessing, it's no surprise the "popularity of letters in our dictionary" is the best letter-guessing fit. The other "popular letters" solutions use a corpuses that are different than the game words will be coming from. However, they don't require ingesting the entire array or using what might be termed "insider information".

In other words, if the list of words is not given at the start of the game, the Oxford or Cornell datasets provide a reasonable alternative. This "insider information" rule also removes the possibility of word guessing.


