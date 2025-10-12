class FiniteStateAutomaton:
    def __init__(self):
        self.states = {'ON', 'OFF', 'START'}
        self.alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '=', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z', '.', 'ç', 'á', 'é', 'õ', ' ', ',', '?', '!', '\n'}
        self.transitions = {'ON': {'0': 'ON', '1': 'ON', '2': 'ON', '3': 'ON', '4': 'ON', '5': 'ON', '6': 'ON', '7': 'ON', '8': 'ON', '9': 'ON', '+': 'ON', '-': 'ON', 'on' : 'ON', 'off' : 'OFF'},
                            'OFF': {'0': 'OFF', '1': 'OFF', '2': 'OFF', '3': 'OFF', '4': 'OFF', '5': 'OFF', '6': 'OFF', '7': 'OFF', '8': 'OFF', '9': 'OFF', '+': 'OFF', '-': 'OFF', 'OFF': 'off', 'ON' : 'on'},
                            'START': {'0': 'START', '1': 'START', '2': 'START', '3': 'START', '4': 'START', '5': 'START', '6': 'START', '7': 'START', '8': 'START', '9': 'START', '+': 'START', '-': 'START', 'on' : 'ON', 'off' : 'OFF'}}
        self.initial_state = 'START'
        self.current_state = ''
        self.accepting_states = {'ON', 'OFF'}
        self.sum_result = 0
        self.temp_number = ""

    def reset(self):
        self.current_state = self.initial_state
        self.sum_result = 0
        self.temp_number = ""

    def process_input(self, input_text):
        i = 0
        error = False

        while i < len(input_text) and not error:
            three_chars = input_text[i:i+3]
            sign = 1
            
            if input_text[i].lower() not in self.alphabet:
                error = True
                error_message = f"Error: symbol '{input_text[i]}' does not belong to the alphabet."
            
            elif input_text[i].lower() == 'o' and input_text[i+1].lower() == 'n':
                i += 1  # Skip two characters after 'on' (one after the loop incrementation and another here)
                self.current_state = 'ON'
            elif 'off' in three_chars.lower():
                self.current_state = 'OFF'
                i += 2  # Skip three characters after 'off' (same here)
            elif input_text[i] == '=':
                print("Current sum:", self.sum_result)
            elif input_text[i] in self.alphabet:
                if self.current_state == 'ON' and input_text[i].isdigit():
                    self.temp_number = ''
                    if i > 0 and input_text[i-1] == '-':
                        sign = -1  # Set sign to negative if '-' is found before the number
                    else:
                        sign = 1  # Set sign to positive if '+' is found before the number or if no sign is found

                    while(i < len(input_text) and input_text[i].isdigit()):
                        self.temp_number += input_text[i]
                        i += 1

                    self.sum_result += sign * int(self.temp_number)
                    self.temp_number = ""
                    i -= 1  # Decrement counter by 1 to account for the incrementation inside the loop

            i += 1
        if error:
            print("Error:", error_message)
            return 
        else:
            if self.current_state in self.accepting_states:
                return self.sum_result
            else:
                print("Semantic error: the automaton did not reach a final state!")
                return


# Create the Finite State Automaton
fsa = FiniteStateAutomaton()

# Process input sequences
input_sequence1 = '3on-1234=a89off456='
result1 = fsa.process_input(input_sequence1)
print(f"Result for '{input_sequence1}': {result1}")
print("\n")

fsa.reset()
three_chars = ""
input_sequence2 = '456on+789ole345=of$f123='
result2 = fsa.process_input(input_sequence2)
print(f"Result for '{input_sequence2}': {result2}")
print("\n")

fsa.reset()
input_sequence3 = '-1234=a89of456='
result3 = fsa.process_input(input_sequence3)
print(f"Result for '{input_sequence3}': {result3}")
print("\n")

fsa.reset()
three_chars = ""
input_sequence4 = open('../input/texto.txt', 'r', encoding='utf-8').read()
result4 = fsa.process_input(input_sequence4)
print(f"Result for 'file': {result4}")
print("\n")

