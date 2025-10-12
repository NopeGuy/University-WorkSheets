import pytest
import hypothesis.strategies as st
from hypothesis import given, settings, HealthCheck
from hypothesis.strategies import composite

# Variáveis globais
output = ""
emails = []
carriers = []
carriersP = []

itemStock = {}
userOrders = {}
userOrdered = {}
usersOrdersId = []
admin_header = "02/01/2023,admin, "
user_header = "02/01/2023, user:"

@pytest.fixture(autouse=True)
def reset_globals():
    global output, emails, carriers, carriersP, itemIds, userOrders, itemStock, userOrdered, usersOrdersId
    output = ""
    emails = []
    carriers = []
    carriersP = []

    userOrders = {}
    itemStock = {}
    userOrdered = {}
    usersOrdersId = []

# Geradores de índices

@composite
def gen_emailIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(emails)-1))

@composite
def gen_itemIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(itemStock)-1))

@composite
def gen_carrierIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(carriers)-1))

@composite
def gen_carrierPIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(carriersP)-1))

@composite
def gen_userOrdersIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(userOrders)-1))

@composite
def gen_userOrderedIndex(draw):
    return draw(st.integers(min_value=0, max_value=len(userOrdered)-1))

# Geradores de criação de usuários
@composite
def gen_email(draw):
    return draw(st.from_regex(r'^[a-zA-Z0-9]{3,15}@[a-zA-Z0-9]{3,10}\.[a-z]{3}$'))
@composite
def gen_nome(draw):
    return draw(st.from_regex(r'^[a-zA-Z]{3,}$'))
@composite
def gen_nif(draw):
    return draw(st.integers(min_value=10000000, max_value=99999999))

@composite
def gen_user(draw):
    email = draw(gen_email()).strip()
    userName = draw(gen_nome()).strip()
    nome = draw(gen_nome()).strip()
    endereco = draw(gen_nome()).strip()
    nif = draw(gen_nif())
    emails.append(email)
    return admin_header + f"user register {email} < {userName} < {nome} < {endereco} < {nif} \n"

@composite
def gen_carrier(draw):
    carrier = draw(gen_nome()).strip()
    carriers.append(carrier)
    return admin_header + f"carrier create {carrier} < n \n"

@composite
def gen_carrierP(draw):
    carrierP = draw(gen_nome()).strip()
    carriersP.append(carrierP)
    return admin_header + f"carrier create {carrierP} < n \n"

# Geradores de criação de itens
@composite
def gen_stock(draw):
    return draw(st.integers(min_value=1, max_value=20))

@composite
def gen_itemId(draw):
    return draw(st.from_regex(r'^[A-Z0-9]{3}\-[A-Z0-9]{3}$'))

@composite
def gen_preco(draw):
    return draw(st.floats(min_value=1, max_value=10000))

@composite
def gen_ano(draw):
    return draw(st.integers(min_value=1000, max_value=3000))

@composite
def gen_depreciation(draw):
    return draw(st.integers(min_value=0, max_value=100))

@composite
def gen_newOrUsed(draw):
    val1 = draw(gen_stock())
    val2 = draw(gen_stock())
    old = "used < " + str(val1) + " < " + str(val2) + "\n"
    new = st.just("new")
    res = draw(st.sampled_from([old, new]))
    return res

@composite
def gen_size(draw):
    return draw(st.sampled_from(["small", "medium", "large", "extra large"]))

@composite
def gen_pattern(draw):
    return draw(st.sampled_from(["plain", "stripes", "palm trees"]))

@composite
def gen_OrderId(draw):
    return draw(st.from_regex(r'^OO[A-Z0-9]\-[A-Z0-9]{3}$'))

@composite
def gen_mala(draw):
    emailId = draw(gen_emailIndex())
    itemId = draw(gen_stock())
    stock = draw(gen_stock())
    newOrUsed = draw(gen_newOrUsed())
    nomeArtigo = draw(gen_nome()).strip()
    marca = draw(gen_nome()).strip()
    preco = draw(gen_preco())
    carrierId = draw(gen_carrierIndex())
    material = draw(gen_nome()).strip()
    ano = draw(gen_ano())
    depreciation = draw(gen_depreciation())
    val2 = draw(gen_stock())

    if(itemStock.get(itemId) == None):
        itemStock[itemId] = int(stock)
    else:
        itemStock[itemId] += int(stock)

    return user_header + f"{emails[emailId]}, item create {itemId} < Mala < {stock} < {newOrUsed} < {nomeArtigo} < {marca} < {preco} < {carriers[carrierId]} < {val2} < {material} < {ano} < {depreciation} \n"

@composite
def gen_malaP(draw):
    emailId = draw(gen_emailIndex())
    itemId = draw(gen_stock())
    stock = draw(gen_stock())
    newOrUsed = draw(gen_newOrUsed())
    nomeArtigo = draw(gen_nome()).strip()
    marca = draw(gen_nome()).strip()
    preco = draw(gen_preco())
    carrierPId = draw(gen_carrierPIndex())
    material = draw(gen_nome()).strip()
    ano = draw(gen_ano())
    depreciation = draw(gen_depreciation())
    val1 = draw(gen_stock())

    if(itemStock.get(itemId) == None):
        itemStock[itemId] = int(stock)
    else:
        itemStock[itemId] += int(stock)

    return user_header + f"{emails[emailId]}, item create {itemId} < Mala Premium < {stock} < {newOrUsed} < {nomeArtigo} < {marca} < {preco} < {carriersP[carrierPId]} < {str(val1)} < {material} < {ano} < {depreciation} \n"

@composite
def gen_sapatilhas(draw):
    emailId = draw(gen_emailIndex())
    itemId = draw(gen_stock())
    stock = draw(gen_stock())
    newOrUsed = draw(gen_newOrUsed())
    nomeArtigo = draw(gen_nome()).strip()
    marca = draw(gen_nome()).strip()
    preco = draw(gen_preco())
    carrierId = draw(gen_carrierIndex())
    ano = draw(gen_ano())
    depreciation = draw(gen_depreciation())
    cor = draw(gen_nome()).strip()
    choice = draw(st.sampled_from(['y','n']))
    if(itemStock.get(itemId) == None):
        itemStock[itemId] = int(stock)
    else:
        itemStock[itemId] += int(stock)

    return user_header + f"{emails[emailId]}, item create {itemId} < Sapatilhas < {stock} < {newOrUsed} < {nomeArtigo} < {marca} < {preco} < {carriers[carrierId]} < {ano} < {depreciation} < {choice} < {cor} \n"

@composite
def gen_sapatilhasP(draw):
    emailId = draw(gen_emailIndex())
    itemId = draw(gen_stock())
    stock = draw(gen_stock())
    newOrUsed = draw(gen_newOrUsed())
    nomeArtigo = draw(gen_nome()).strip()
    marca = draw(gen_nome()).strip()
    preco = draw(gen_preco())
    carrierPId = draw(gen_carrierPIndex())
    ano = draw(gen_ano())
    depreciation = draw(gen_depreciation())
    cor = draw(gen_nome()).strip()
    val1 = draw(gen_stock())
    val2 = draw(st.sampled_from(['y', 'n']))


    if(itemStock.get(itemId) == None):
        itemStock[itemId] = int(stock)
    else:
        itemStock[itemId] += int(stock)

    return user_header + f"{emails[emailId]}, item create {itemId} < Sapatilhas Premium < {stock} < {newOrUsed} < {nomeArtigo} < {marca} < {preco} < {carriersP[carrierPId]} < {ano} < {depreciation} < {str(val1)} < {val2} < {cor} \n"

@composite
def gen_tshirt(draw):
    emailId = draw(gen_emailIndex())
    itemId = draw(gen_stock())
    stock = draw(gen_stock())
    newOrUsed = draw(gen_newOrUsed())
    nomeArtigo = draw(gen_nome()).strip()
    marca = draw(gen_nome()).strip()
    preco = draw(gen_preco())
    carrierId = draw(gen_carrierIndex())
    size = draw(gen_size())
    pattern = draw(gen_pattern())

    if(itemStock.get(itemId) == None):
        itemStock[itemId] = int(stock)
    else:
        itemStock[itemId] += int(stock)

    return user_header + f"{emails[emailId]}, item create {itemId} < TShirt < {int(stock)} < {newOrUsed} < {nomeArtigo} < {marca} < {preco} < {carriers[carrierId]} < {size} < {pattern} \n"


# Geradores de Item stock
@composite
def gen_itemStockAdd(draw):
    itemIndex = draw(gen_itemIndex())
    itemId = list(itemStock.keys())[itemIndex]
    stock = st.integers(min_value=1, max_value=3)
    itemStock[itemId]+=1

    return user_header + f" item stock add {itemId} {stock} \n"

@composite
def gen_itemStockRem(draw):
    itemIndex = draw(gen_itemIndex())
    itemId = list(itemStock.keys())[itemIndex]

    while(itemStock[itemId]==0):
        itemStock.pop(itemId)
        itemId = draw(gen_itemIndex())

    stock = st.integers(min_value=1, max_value=3)

    return user_header + f" item stock remove {itemId} {stock} \n"

# Geradores de carrinho
@composite
def gen_addCart(draw):
    emailId = draw(gen_emailIndex())
    orderId = draw(gen_OrderId()).strip()
    itemId = draw(gen_itemIndex())

    userOrders[orderId] = (emailId, [itemId])

    usersOrdersId.append(orderId)

    return user_header + f"{emails[emailId]}, cart add {itemId} < y \n"

@composite
def gen_orderCart(draw):
    userOrdersId = draw(gen_userOrdersIndex())
    orderId = list(userOrders.keys())[userOrdersId]
    userId = userOrders[orderId][0]
    orderItemsList = userOrders[orderId][1]
    userOrdered[orderId] = (userId, orderItemsList)
    for item in orderItemsList:
        if itemStock.get(item, 0) == 0:
            return ""  # If any item is out of stock, do not process the order
        itemStock[item] -= 1  # Reduce the stock by 1 for each item in the order
    userOrders.pop(orderId)
    return user_header + f"{emails[userId]}, cart order < y \n"


@composite
def gen_returnOrder(draw):
    userOrderedId = draw(gen_userOrderedIndex())
    orderId = list(userOrdered.keys())[userOrderedId]
    userId, orderItemsList = userOrdered[orderId]
    for item in orderItemsList:
        itemStock[item] += 1
    userOrdered.pop(orderId)
    return f"05/01/2023, user:{emails[userId]}, order return {orderId} \n"



if __name__ == "__main__":
    output=""
    userNum=st.integers(min_value=10, max_value=30).example()
    for(i) in range(userNum):
        output+=gen_user().example()
    
    carriersNum=st.integers(min_value=1, max_value=5).example()
    for(i) in range(carriersNum):
        output+=gen_carrier().example()
        output+=gen_carrierP().example()

    itemsNum=st.integers(min_value=5, max_value=10).example()
    for(i) in range(itemsNum):
        output+=gen_mala().example()
        output+=gen_malaP().example()
        output+=gen_sapatilhas().example()
        output+=gen_sapatilhasP().example()
        output+=gen_tshirt().example()
    
    stockChanges=st.integers(min_value=0, max_value=5).example()
    for(i) in range(stockChanges):
        output+=gen_itemStockAdd().example()
        output+=gen_itemStockRem().example()
        
   # ordersNum=st.integers(min_value=5, max_value=10).example()
    #for(i) in range(ordersNum):
        #output+=gen_addCart().example()
        #output+=gen_orderCart().example()
        #output+=gen_returnOrder().example()


    with open("logTP3.txt", "w") as f:
        f.write(output)
