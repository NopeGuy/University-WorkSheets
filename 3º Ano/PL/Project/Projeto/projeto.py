import ply.lex as lex
import ply.yacc as yacc

#Estados possíveis 
states = (
    ('functionDefState','inclusive'), # Definição lógica da função
    ('funInpState','exclusive'), # Declaração de input de funções
    ('funOutState','exclusive'), # Declaração de output de funções
    ('conditionalState','inclusive'), # Inicia com IF
)

#Tokens possíveis
tokens = (
'NUMBER',
'ADD',
'SUB',
'MUL',
'DIV',
'MOD',
'EQUALS',
'DIFF',
'GREQUAL', # >=
'LESEQUAL', # <=
'LESSER', # <
'GREATER', # >
'COMMENT', # \ comentario
'COMMENT2', # ( comentario )
'FUNCTIONSTART', # :
'FUNCTIONEND', # ;
'FUNIN', # ( |a b| -- ... )
'FUNOUT', # ( ... -- |out1 out2| )
'LPAREN', # (
'RPAREN', # )
'ARGSEP', # Separador de input/output das funções  --
'WORD', # Funções
'POPPRINT', # .
'PRINTDELIM', # ."
'EMIT', 
'KEY', 
'SPACES', 
'SPACE', 
'CHAR', # Função FORTH 
'CR',
'IF',
'THEN',
'ELSE',
'SWAP',
)

#COMENTARIOS

def t_ANY_COMMENT(t):
    r'\\\s.+\n'

def t_ANY_COMMENT2(t):
    r'\(\s.+\s\)'

#FIM COMENTARIOS

#CONDICIONAIS

def t_INITIAL_IF(t):
    r'[Ii][fF]\b'
    t.lexer.push_state('conditionalState')
    return t

def t_INITIAL_conditionalState_ELSE(t):
    r'[Ee][Ll][sS][eE]\b'
    return t
    
def t_INITIAL_conditionalState_THEN(t):
    r'[Tt][Hh][Ee][nN]\b'
    t.lexer.pop_state()
    return t

#FIM CONDICIONAIS

#FUNCOES

def t_FUNCTIONSTART(t):
    r':\s'
    t.lexer.push_state('functionDefState')
    return t

def t_FUNCTIONEND(t):
    r';(?!.*;)'
    t.lexer.pop_state()
    return t

##COMENTARIO DE FUNCOES

def t_functionDefState_LPAREN(t):
    r'\(\s'
    t.lexer.push_state('funInpState')
    print("Entered funInpState")
 
def t_funOutState_RPAREN(t):
    r'\s\)'
    t.lexer.pop_state()
    print("Exited funOutState")

def t_funInpState_ARGSEP(t):
    r'\s--\s'
    t.lexer.pop_state()
    t.lexer.push_state('funOutState')
    print("Entered funOutState")

def t_funInpState_FUNIN(t):
    r'[^-\s\n]+'


def t_funOutState_FUNOUT(t):
    r'[^\s\)\n]+'

##FIM COMENTARIO DE FUNCOES


# FUNCOES PRE-DEFINIDAS

def t_INITIAL_conditionalState_PRINTDELIM(t): 
    r'\."\s[^"]+"'
    t.value = t.value[3:-1]
    return t

def t_INITIAL_conditionalState_POPPRINT(t): 
    r'\.'                           
    return t

def t_INITIAL_conditionalState_KEY(t): 
    r'[Kk][eE][yY]\b'                           
    return t

def t_INITIAL_conditionalState_SPACES(t): 
    r'[sS][pP][aA][cC][eE][sS]\b'
    return t

def t_INITIAL_conditionalState_SPACE(t): 
    r'[sS][pP][aA][cC][eE]\b'                           
    return t

def t_INITIAL_conditionalState_CR(t):
    r'[cC][rR]\b'
    return t

def t_INITIAL_conditionalState_CHAR(t):
    r'[cC][hH][aA][rR]\b'
    return t

def t_INITIAL_conditionalState_EMIT(t):
    r'[eE][mM][iI][tT]\b'
    return t

def t_INITIAL_conditionalState_NUMBER(t):
    r'\d+'
    return t

def t_INITIAL_conditionalState_SWAP(t):
    r'[sS][wW][aA][pP]\b'
    return t

def t_INITIAL_conditionalState_WORD(t): 
    r'[A-Za-z0-9]+'                          
    return t


t_INITIAL_conditionalState_ADD = r'\+'
t_INITIAL_conditionalState_SUB = r'-'
t_INITIAL_conditionalState_MUL = r'\*'
t_INITIAL_conditionalState_DIV = r'/'
t_INITIAL_conditionalState_MOD = r'%'
t_INITIAL_conditionalState_DIFF = r'<>'
t_INITIAL_conditionalState_GREQUAL = r'>='
t_INITIAL_conditionalState_LESEQUAL = r'<='
t_INITIAL_conditionalState_EQUALS = r'='
t_INITIAL_conditionalState_LESSER = r'<'
t_INITIAL_conditionalState_GREATER = r'>'


# IGNORES

t_ignore = ' \t\n'
t_funInpState_funOutState_ignore = ' \t\n'
t_conditionalState_ignore = ' \t\n'

# FIM IGNORES

def t_ANY_error(t):
    print('Illegal character: ',t.value[0],' Pos: ',t.lexpos)
    t.lexer.skip(1)

# MAIN

lexer = lex.lex(#debug=True
    )

with open('input.txt', 'r') as file:
    forth = file.read()
 



lexer.input(forth)

# print results from lexer
# while tok := lexer.token():
#    print(tok)

precedence = (
    ('left','WORD'),
    ('left','FUNCTIONEND'),
    ('right','CHAR'),
    ('left','SPACES','EMIT'),
    ('left', 'EQUALS', 'DIFF', 'GREQUAL', 'LESEQUAL', 'LESSER', 'GREATER'),
    ('left', 'ADD', 'SUB', 'MUL', 'DIV', 'MOD'),
    ('left', 'THEN'),
    ('right', 'ELSE'),
    ('right', 'IF'),   
)

def p_exps(p):
    '''exps : exps exp'''
    p[0] = p[1] + p[2]

def p_exps_empty(p):
    '''exps : empty'''
    p[0] = ""
    

def p_exp_word(p):
    '''exp : WORD'''
    p[0] = ""
    if p[1] not in parser.enderecos and p[1] != parser.func:
        print("Function '" + p[1] + "' not defined.")
        raise SyntaxError
    
    funInfo = parser.enderecos.get(p[1]) # Encontra a função no dicionário

    if parser.funDef == False:
        p[0] = funInfo['body'] # Adicionar corpo da função ao código
        parser.i += funInfo['saldo'] # Adiciona o saldo da função ao contador
    else:
        p[0] = funInfo['body']
        funAdef = parser.enderecos.get(parser.func)
        funAdef['saldo'] += funInfo['saldo']


def p_funStarted_WORD(p):
    '''funStarted : FUNCTIONSTART WORD'''
    p[0] = ""
    if p[2] in parser.enderecos:
        print('Function ' + p[2] + ' already exists.')
        raise SyntaxError
    parser.funDef = True
    parser.func = p[2]
    data = ({p[2]: {'body':"",'saldo':0}})
    parser.enderecos.update(data)
    

def p_functionBody(p):
    '''functionBody : exps FUNCTIONEND'''
    p[0] = ""
    nome = parser.func
    bodyFun = p[1]

    parser.enderecos[nome]['body'] = bodyFun
    parser.enderecos[nome]['saldo'] = parser.funCount

    #Reiniciar Variáveis globais
    parser.funCount = 0
    parser.func = ""
    parser.funDef = False
    

def p_exp_funDefined(p):
    '''exp : funStarted functionBody'''
    p[0] = ""

    
def p_exp_aritOnly(p):
    '''exp : ADD
           | SUB
           | MUL
           | DIV
           | MOD'''

    if parser.funDef == False:
        if parser.i < 2:
            print("Arithmetic Error: Not enough values on stack!")
        parser.i -= 1
    else:
        parser.funCount -=1

    if p[1] == '+':
        p[0] = 'ADD\n'
    elif p[1] == '-':
        p[0] = 'SUB\n'
    elif p[1] == '/':
        p[0] = 'DIV\n'
    elif p[1] == '*':
        p[0] = 'MUL\n'
    elif p[1] == '%':
        p[0] = 'MOD\n'

def p_exp_relOpOnly(p):
    '''exp : LESSER
            | GREATER
            | DIFF
            | GREQUAL
            | LESEQUAL
            | EQUALS'''

    if parser.funDef == False:
        if parser.i < 2:
            print("Logical Error: Not enough values on stack!")
            raise SyntaxError
        parser.i -= 1
    else:
        parser.funCount -=1

    if p[1] == '<':
        p[0] = 'INF\n'
    elif p[1] == '>':
        p[0] = 'SUP\n'
    elif p[1] == '<>':
        p[0] = 'NOT\n'
    elif p[1] == '<=':
        p[0] = 'INFEQ\n'
    elif p[1] == '>=':
        p[0] = 'SUPEQ\n'
    elif p[1] == '=':
        p[0] = 'EQUAL\n'

def p_exp_ifThen(p):
    '''exp : IF exps THEN'''
    if parser.funDef == False:
        if parser.i < 1:
            print("Conditional Error: Not enough values on stack!")
            raise SyntaxError
        parser.i -= 1
    else:
        parser.funCount -= 1

    false = 'l' + str(parser.labels)
    p[0] = 'JZ ' + false + '\n' # Salto caso seja falso
    p[0] += p[2] # Condição caso verdadeiro
    p[0] += false + ':\n' # Salto (falso)
    parser.labels+=1

def p_exp_ifElseThenOnly(p):
    '''exp : IF exps ELSE exps THEN'''
    if parser.funDef == False:
        if parser.i < 1:
            print("Conditional Error: Not enough values on stack!")
            raise SyntaxError
        parser.i -= 1
    else:
        parser.funCount -= 1
    
    fi = 'l' + str(parser.labels)
    parser.labels+=1
    els = 'l' + str(parser.labels)
    parser.labels+=1
    fim = 'l' + str(parser.labels)
    p[0] = 'JZ ' + els + '\n' # Salto caso seja falso
    p[0] += p[2] # V
    p[0] += 'jump ' + fim + '\n' # Salto para o fim
    p[0] += els + ':\n' # Salto F
    p[0] += p[4] # F
    p[0] += fim + ':\n' # fim
    
    parser.labels+=1


def p_exp_charOnly(p):
    '''exp : CHAR WORD'''

    if len(p[2]) > 1:
        print("SyntaxError - CHAR applied to more than one character.")
        raise SyntaxError

    p[0] = 'pushs "' + p[2] + '"\n'
    p[0] += 'CHRCODE\n'

    if parser.funDef == False:
        parser.i += 1
    else:
        parser.funCount +=1


def p_exp_printOnly(p):
    '''exp : PRINTDELIM
           | CR
           | SPACE
           | KEY'''
    if p[1] == 'CR':
        p[0] = 'writeln\n'
    elif p[1] == 'SPACE':
        p[0] = 'pushs " " \n'
    elif p[1] == 'KEY':
        p[0] = 'read\n'
        p[0] += 'chrcode\n'
        if parser.funDef == False:
            parser.i += 1
        else:
            parser.funCount +=1
    else: # PRINTDELIM
        p[0] = 'pushs "' + p[1] + '"\n'
        p[0] += 'writes \n'


def p_numExp_prints(p):
    '''exp : SPACES 
           | EMIT'''
    
    strt = 'l' + str(parser.labels) 
    parser.labels +=1
    fin = 'l' + str(parser.labels) 
    parser.labels +=1
    if parser.funDef == False:
        if parser.i <= 0:
            print('Syntax Error on SPACES or EMIT function. Not enough arguments.\n')
            raise SyntaxError
        parser.i -= 1
    else:
        parser.funCount -=1

    if p[1] == 'SPACES':
        #loop core 1
        p[0] += strt + ':\n'
        p[0] += 'dup 1\npushi 0\nsup\njz ' + fin + '\n'
        #print espaço
        p[0] += 'pushs " "\nwrites\n'
        #loop core 2
        p[0] += 'pushi 1\nsub\njump ' + strt + '\n'
        p[0] += fin + ':\n' + 'pop 1\n'
    else: # EMIT
        p[0] = 'writechr\n'
    

def p_num_number(p):
    '''num : NUMBER'''
    p[0] = 'pushi ' + p[1] + '\n'
    if parser.funDef == False:
        parser.i += 1
    else:
        parser.funCount +=1

def p_numExp_nums(p):
    '''exp : num'''
    p[0] = p[1]

def p_exp_pop(p):
    '''exp : POPPRINT'''
    p[0] = 'writei\n'
    if parser.funDef == False:
        if parser.i <= 0:
            print('Index Error on pop. Not enough arguments.\n')
            raise IndexError 

        parser.i -= 1
    else:
        parser.funCount -=1

def p_exp_swapOnly(p):
    '''exp : SWAP'''
    p[0] = 'swap\n'

def p_empty(p):
    'empty :'
    pass

# Error Handling

def p_error(p):
    print("Syntax error in input!")
    raise SyntaxError

parser = yacc.yacc()

parser.enderecos = {} # key: WORD; body, saldo
parser.labels = 1
parser.i = 0
parser.funDef = False
parser.func = ""
parser.funCount = 0

res = 'START\n'
res += str(parser.parse(forth, lexer=lexer
                        #,debug=True
                        ))
res += 'STOP'

with open('output.txt', 'w') as file:
    file.write(res)