## Assumptions:

* I'm only maintaining state in the Solver. Since the HangmanGame is passed in, I should probably use it for state.
* Copied words.txt to net.inervo so the Dictionary can see it. It's not a production way to handle the dependency, I just wanted to get it working.
* I avoided researching algorithms that are used to solve similar problems. Generally it's good to not reinvent the world but it felt outside the spirit of the problem.
* getScore made it difficult to collect statistics, so the code avoids calling it.
* There's zero thought given to different alphabets. The solutions will likely fail in an unknown and spectacular manner.

## Next 'technical debt' steps:

* The various switches and levers are buried in weird places. For instance, Runner contains the list of solvers that are used, number of guesses allowed, and the number of words in the game, but SolutionStatistics has the hardcoded "probability of a solution by 5" number.
* words.txt isn't being loaded properly by Dictionary, so I copied it to the net.inervo.hangman directory.
* The .classpath contains a hardcoded directory path to commons-io on my personal development machine. I don't think there are any other local-machine dependencies.  
* More unit tests.
* jdoc-style documentation. Comments exist but I've made no attempt to document the code for outsiders.

## Next algorithmic steps:

* No effort has been taken to use existing information to choose the next letter. In other words, "given Q, suggest U".
* Add word guessing. If there are less possible words than remaining letters, this would be very effective.
* A corpus including word popularity would likely work well with human-chosen words.
* Research common/related algorithms.

## Changes to provided code:

* Added a package declaration
* Made HangmanGame and HangmanGame.Status public.

## Sample output

### guesses=6

Output with "number of guesses" set to 6. This provides less information on the average number of guesses to provide a solution. (in fact, the avg_guesses is basically noise)

```
net.inervo.hangman.solver.MostPopularOurDictionary      games=1000  avg_guesses=06.493000 prob_below_5=0.082000
net.inervo.hangman.solver.RandomOrder                   games=1000  avg_guesses=06.996000 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularCornell            games=1000  avg_guesses=06.634000 prob_below_5=0.059000
net.inervo.hangman.solver.MostPopularOxford             games=1000  avg_guesses=06.674000 prob_below_5=0.054000
net.inervo.hangman.solver.SequentialOrder               games=1000  avg_guesses=06.989000 prob_below_5=0.001000
net.inervo.hangman.solver.ReverseSequentialOrder        games=1000  avg_guesses=06.998000 prob_below_5=0.000000
```

### guesses=99

With the "number of guesses" set to 99. This captures all the information above but is different than the expected output to the problem. Note the avg_guesses data gives a much better indication of solution fitness.

```
net.inervo.hangman.solver.MostPopularOurDictionary      games=1000  avg_guesses=09.991000 prob_below_5=0.078000
net.inervo.hangman.solver.RandomOrder                   games=1000  avg_guesses=16.389000 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularCornell            games=1000  avg_guesses=11.053000 prob_below_5=0.057000
net.inervo.hangman.solver.MostPopularOxford             games=1000  avg_guesses=10.600000 prob_below_5=0.059000
net.inervo.hangman.solver.SequentialOrder               games=1000  avg_guesses=13.736000 prob_below_5=0.001000
net.inervo.hangman.solver.ReverseSequentialOrder        games=1000  avg_guesses=17.340000 prob_below_5=0.001000
```

### Specific words given in problem:

The number of words that were determined successfully by these six algorithms are *substantially* lower than the implied solutions in the problem email. Admittedly, those were guessed with a combination of letter+word guesses, where the six solutions given here are purely based on letter guessing.

```
net.inervo.hangman.solver.MostPopularOurDictionary      games=15    avg_guesses=09.533333 prob_below_5=0.066667
net.inervo.hangman.solver.RandomOrder                   games=15    avg_guesses=16.266667 prob_below_5=0.000000
net.inervo.hangman.solver.MostPopularCornell            games=15    avg_guesses=09.600000 prob_below_5=0.133333
net.inervo.hangman.solver.MostPopularOxford             games=15    avg_guesses=09.800000 prob_below_5=0.133333
net.inervo.hangman.solver.SequentialOrder               games=15    avg_guesses=13.666667 prob_below_5=0.000000
net.inervo.hangman.solver.ReverseSequentialOrder        games=15    avg_guesses=17.266667 prob_below_5=0.000000
```

## Output commentary

It's no surprise the "popularity of letters in our dictionary" is the best fit. The other "popular letters" solutions use a corpuses that are different than the game words will be coming from. However, they don't require ingesting the entire array or using what might be termed "insider information".

In other words, if the list of words is not given at the start of the game, the Oxford or Cornell datasets provide a reasonable alternative.


