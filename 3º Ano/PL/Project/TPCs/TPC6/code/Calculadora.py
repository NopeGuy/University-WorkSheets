class LL1CalculatorWithVars:
    def __init__(self, input):
        self.lines = input.split('\n')
        self.vars = {} 

    def match(self, token, lookahead):
        if lookahead == token:
            self.index += 1
            if self.index < len(self.current_line):
                return self.current_line[self.index]
        else:
            raise ValueError(f"Token inesperado: {lookahead}")

    def factor(self, lookahead):
        if lookahead.isdigit():
            start = self.index
            while lookahead.isdigit():
                lookahead = self.match(lookahead, lookahead)
            return int(self.current_line[start:self.index]), lookahead
        elif lookahead == '(':
            lookahead = self.match('(', lookahead)
            result, lookahead = self.expr(lookahead)
            lookahead = self.match(')', lookahead)
            return result, lookahead
        elif lookahead.isalpha():
            var_name = ''
            while lookahead.isalpha():
                var_name += lookahead
                self.index += 1
                lookahead = self.current_line[self.index] if self.index < len(self.current_line) else '$'
            return self.vars.get(var_name, 0), lookahead
        else:
            raise ValueError("Símbolo inesperado")

    def term(self, lookahead):
        result, lookahead = self.factor(lookahead)
        while lookahead in ['*', '/']:
            if lookahead == '*':
                lookahead = self.match('*', lookahead)
                factor_result, lookahead = self.factor(lookahead)
                result *= factor_result
            elif lookahead == '/':
                lookahead = self.match('/', lookahead)
                factor_result, lookahead = self.factor(lookahead)
                result /= factor_result
        return result, lookahead

    def expr(self, lookahead):
        result, lookahead = self.term(lookahead)
        while lookahead in ['+', '-']:
            if lookahead == '+':
                lookahead = self.match('+', lookahead)
                term_result, lookahead = self.term(lookahead)
                result += term_result
            elif lookahead == '-':
                lookahead = self.match('-', lookahead)
                term_result, lookahead = self.term(lookahead)
                result -= term_result
        return result, lookahead

    def parse_line(self, line):
        self.current_line = line.replace(" ", "") + "$"
        self.index = 0
        lookahead = self.current_line[self.index]

        if lookahead == '?':
            self.index += 1
            lookahead = self.current_line[self.index]
            var_name = ''
            while lookahead.isalpha():
                var_name += lookahead
                self.index += 1
                lookahead = self.current_line[self.index]
            valor = input(f"Por favor, insira um valor para '{var_name}': ")
            try:
                valor_numerico = float(valor)
                self.vars[var_name] = valor_numerico
            except ValueError:
                raise ValueError("Valor inválido. Por favor, insira um número.")
        elif lookahead.isalpha() and '=' in self.current_line:
            var_name = ''
            while lookahead != '=':
                var_name += lookahead
                self.index += 1
                lookahead = self.current_line[self.index]
            self.index += 1
            lookahead = self.current_line[self.index]
            value, lookahead = self.expr(lookahead)
            self.vars[var_name] = value
        elif lookahead == '!':
            self.index += 1
            lookahead = self.current_line[self.index]
            result, _ = self.expr(lookahead)
            print(result)

    def calculate(self):
        for line in self.lines:
            self.parse_line(line)

program = """
? a
b = (a * 2) / (27 - 2)
! a + b
c = (a * b) / (a / b)
! c
"""
calculator = LL1CalculatorWithVars(program)
calculator.calculate()