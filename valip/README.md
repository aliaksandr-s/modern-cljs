# Valip

Valip is a validation library for Clojure. It is primarily designed to validate
keyword-string maps, such as one might get from a HTML form.

**This is an experimental fork of [James
Reeves](http://github.com/weavejester)'
[valip](http://github.com/weavejester/valip) library.**  The differences
between this and that are significant:

1. I'm trying to make this fork as portable as possible between Clojure and
   (JavaScript) ClojureScript.
2. Since I'm breaking stuff anyway, I'm making all sorts of changes and
   additions to the original predicates and such, hopefully all for the better.

# Installation

Add the following dependency to your `project.clj` file:

    [com.cemerick/valip "0.3.2"]

# Usage

The main validation function is `valip.core/validate`. It uses the following
syntax:

    (validate map-of-values
      [key1 predicate1 error1]
      [key2 predicate2 error2]
      ...
      [keyn predicaten errorn])

For each vector, the key is used to look up a value in the map. The map value
is then tested with the predicate function. If the predicate fails, the error
message is included in the map of errors returned by the `validate` function. A
key may be tested multiple times with different predicates and errors.

If no predicate fails, `nil` is returned. If at least one predicate fails, a
map of keys to errors is returned:

    {key1 [error1]
     key2 [error2]
     ...
     keyn [errorn]}

The errors are listed in a vector, because there may be multiple errors for the
same key.

For example:

    (use 'valip.core 'valip.predicates)

    (def user
      {:name "Alice", :age 7})

    (validate user
      [:name present? "must be present"]
      [:age present? "must be present"]
      [:age (over 18) "must be over 18"])

    => {:age ["must be over 18"])

# Predicates

Valip has a number of useful predicates and functions that generate predicates.
More of these useful predicates will be added as the library matures.

You can find portable predicates in the `valip.predicates` namespace.
Platform-specifc predicates can be found in `valip.java.predicates` and
`valip.js.predicates`, depending on your deployment target.

## License

Copyright © 2012 James Reeves

Distributed under the Eclipse Public License, the same as Clojure.
