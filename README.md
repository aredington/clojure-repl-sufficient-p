# clojure-repl-sufficient-p

A Clojure repository of some REPL sessions that illustrate the why
evaluating individual forms is not a sufficient or sustainable
practice for developing Clojure programs. Clojure has many good
decisions, but how it iteratively maintains its environment is not
conducive to evaluating individual forms for iteratively developing.

Some prominent voices claim that tools which address these problems by
implementing 100% solutions are overkill, or are wasteful, or are
cargo culting. This repository demonstrates specific use cases which:
utilize only features of Clojure the language, are practical uses of
the Clojure language, cannot be ameliorated by system structure, and
are feasible to correct. If this definition is not descriptive, it is
at least aspirational and the contents should converge toward this
definition.

## Naming

Many older lisps did not accommodate the `?` character in function
names, so when a function was a predicate, it would be anteceded by
`-p`. As this project is intended as a predicate of a particular
version of the Clojure language, and it is hosted on GitHub by HTTP,
the `?` would be similarly inconvenient to include in the URL for this
repo. As a cheeky callback to older lisps and a way around this
quality of HTTP, this repo is so named.

## Usage

This repository contains files intended to be representative of REPL
interactions, as well as example based tests which automatically load
these files and assert the expected pre- and post-conditions if
evaluating individual forms at the REPL was sufficient for iterative
development.

## Contributing

This repository is a tool for constructive criticism of the Clojure
language. Contributions in the form of new namespaces to add to the
'language-test' testing ns may be accepted if:

- The namespace mocks a REPL session where some code is modified in an
  iterative manner.
- The namespace clearly captures the pre- and post-conditions that the
  iterative modification affects.
- The namespace uses a feature of Clojure the language
- The namespace demonstrates a practical use of that feature when
  building software that helps humans.
- The problem demonstrated cannot be solved by a different code
  organization, or by a third party library.
- The problem identified may be feasibly corrected.

Contributions which do not meet this standard will be rejected out of
hand.

## License

Copyright © 2019 Alex Redington

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
