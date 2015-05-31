package com.sokrati.sokexmobile.model;

import java.util.Map;

public interface ModelSerializer
{
    String serialize(Map<String, Object> map);
}
// serialize means:-
// helps to write object to file..(simplest)
// deserialize :-
// reads from file, and helps to create object of this
// here serialize is implemented as a string.. not file
// either http get string format or a json string format