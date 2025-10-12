import os
from RegexConvFullFileRead import RegexConvFull  
from RegexConvPerLine import RegexConvPerLine

class Main:
    @staticmethod
    def read():
        print("Escolha converter o ficheiro linha a linha ou inteiramente.")
        print("1. Converter ficheiro linha a linha")
        print("2. Converter ficheiro inteiramente")
        choice = input("Escolha: ")
        input_folder = os.path.join(os.path.dirname(__file__), '..', 'input')
        output_folder = os.path.join(os.path.dirname(__file__), '..', 'output')

        os.makedirs(input_folder, exist_ok=True)

        md_files = [f for f in os.listdir(input_folder) if f.endswith('.md')]
        if not md_files:
            print("A pasta 'input' está vazia ou não contém ficheiros Markdown (.md). Insira os ficheiros na pasta 'input'.")
            return

        os.makedirs(output_folder, exist_ok=True)

        if choice == "1":
            for file_name in md_files:
                file_path = os.path.join(input_folder, file_name)
                html_output = RegexConvPerLine.markdown_to_html(file_path)
                
                output_path = os.path.join(output_folder, f"{os.path.splitext(file_name)[0]}.html")
                
                with open(output_path, "w") as output_file:
                    output_file.write(html_output)
                print(f"Conversão bem sucedida. Ficheiro HTML guardado em '{output_path}'")
        
        
        if choice == "2":
            for file_name in md_files:
                file_path = os.path.join(input_folder, file_name)
                try:
                    with open(file_path, 'r') as file:
                        markdown_text = file.read()
                        html_output = RegexConvFull.markdown_to_html(markdown_text)
                        
                        output_path = os.path.join(output_folder, f"{os.path.splitext(file_name)[0]}.html")
                        
                        with open(output_path, "w") as output_file:
                            output_file.write(html_output)
                        print(f"Conversão bem sucedida. Ficheiro HTML guardado em '{output_path}'")
                except FileNotFoundError:
                    print(f"File not found: {file_path}")
                
                
                
    def __init__(self):
        Main.read()
        

main_instance = Main()
