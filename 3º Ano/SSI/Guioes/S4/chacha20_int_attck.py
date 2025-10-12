import sys


def xor_bytes(b1, b2):
    return bytes(x ^ y for x, y in zip(b1, b2))

def find_key_and_replace(encrypted_text, position, decrypted_word, new_word_at_position):
    nonce = encrypted_text[:8]
    ciphertext = encrypted_text[8:]
    cipherword = encrypted_text[position + 8 : position + len(decrypted_word) + 8]

    decrypted_word_bytes = decrypted_word.encode('utf-8')
    new_word_bytes = new_word_at_position.encode('utf-8')

    key = xor_bytes(cipherword, decrypted_word_bytes)

    encrypted_new_word = xor_bytes(new_word_bytes, key)

    updated_ciphertext = (
        ciphertext[: position]
        + encrypted_new_word
        + ciphertext[position + len(decrypted_word_bytes) :]
    )

    updated_encrypted_text = nonce + updated_ciphertext

    print(f"Key found: {key}")
    return key, updated_encrypted_text

if __name__ == "__main__":
    
    encrypted_file_name = sys.argv[1]
    position = int(sys.argv[2])
    decrypted_word = sys.argv[3]
    new_word_at_position = sys.argv[4]
    with open(encrypted_file_name, "rb") as encrypted_file:
        encrypted_text = encrypted_file.read()

    key, updated_encrypted_text = find_key_and_replace(
        encrypted_text, position, decrypted_word, new_word_at_position
    )

    if key and updated_encrypted_text:
        with open("encrypted_file.enc", "wb") as encrypted_file:
            encrypted_file.write(updated_encrypted_text)
    else:
        print("Replacement unsuccessful.")
