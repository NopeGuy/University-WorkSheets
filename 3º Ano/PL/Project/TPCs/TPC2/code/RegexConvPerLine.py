import re

class RegexConvPerLine:
    
    @staticmethod
    def markdown_to_html(file_path):
        md = {
            "h1": re.compile(r'^#\s*(.+)'),
            "h2": re.compile(r'^##\s*(.+)'),
            "h3": re.compile(r'^###\s*(.+)'),
            "bold": re.compile(r'\*\*(.+?)\*\*'),
            "italic": re.compile(r'\*(.+?)\*'),
            "italicgoat": re.compile(r'\*(?![*])([^*]*[^*])\*(?!\*)'),
            "blockquote": re.compile(r'^\s*(?<!.)>\s*(.+)'),
            "orderedList": re.compile(r'\d+\. (.+)'),
            "unorderedList": re.compile(r'\- (.+)'),
            "code": re.compile(r'`(.+)`'),
            "horizontalRule": re.compile(r'^---'),
            "link": re.compile(r'\[(.*)\]\((.*)\)'),
            "image": re.compile(r'!\[(.*)\]\((.*)\)')
        }
        
        file = open(file_path, "r")
        converted_lines = []

        current_list_type = None

        for line in file:
            markdown_text = line
            
            # Convert Títulos
            markdown_text = re.sub(md["h3"], r'<h3>\1</h3>', markdown_text)
            markdown_text = re.sub(md["h2"], r'<h2>\1</h2>', markdown_text)
            markdown_text = re.sub(md["h1"], r'<h1>\1</h1>', markdown_text)
            
            # Convert Negrito e Itálico
            markdown_text = re.sub(md["bold"], r'<b>\1</b>', markdown_text)
            markdown_text = re.sub(md["italic"], r'<em>\1</em>', markdown_text)
            
            # Convert Blockquote
            markdown_text = re.sub(md["blockquote"], r'<blockquote>\1</blockquote>', markdown_text)
            
            # Convert Listas
            if re.match(md["orderedList"], line):
                if current_list_type != "ol":
                    converted_lines.append('<ol>')
                    current_list_type = "ol"
                markdown_text = re.sub(md["orderedList"], r'<li>\1</li>', markdown_text)
            elif re.match(md["unorderedList"], line):
                if current_list_type != "ul":
                    converted_lines.append('<ul>')
                    current_list_type = "ul"
                markdown_text = re.sub(md["unorderedList"], r'<li>\1</li>', markdown_text)
            else:
                if current_list_type:
                    converted_lines.append(f'</{current_list_type}>\n')
                    current_list_type = None
            
            # Convert Código
            markdown_text = re.sub(md["code"], r'<code>\1</code>', markdown_text)
            
            # Convert Linha Horizontal
            markdown_text = re.sub(md["horizontalRule"], r'<hr>', markdown_text)
            
            # Convert Imagens
            markdown_text = re.sub(md["image"], r'<img src="\2" alt="\1">', markdown_text)
            
            # Convert Links
            markdown_text = re.sub(md["link"], r'<a href="\2">\1</a>', markdown_text)
            
            converted_lines.append(markdown_text)

        if current_list_type:
            converted_lines.append(f'</{current_list_type}>\n')

        return ''.join(converted_lines)
