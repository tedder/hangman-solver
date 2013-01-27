Assumptions:
* I'm only maintaining state in the Solver. Since the HangmanGame is passed in, I should probably use it for state.
* Copied words.txt to net.inervo so the Dictionary can see it. It's not a production way to handle the dependency, I just wanted to get it working.

Changes to provided code:
* Added a package declaration
* Made HangmanGame and HangmanGame.Status public.