import xml.etree.ElementTree as ET

def modify(orig_str, new_chars, n2):
    # 将第3、4个字符更变
    modified_str = orig_str[:2] + new_chars + n2 + orig_str[6:]
    # 将第7个字符改为 'f'
    #modified_str = modified_str[:6] + 'f' + modified_str[7:]
    #modified_str = modified_str[:7] + '4' + modified_str[8:]
    return modified_str

replacement_dict = {
    "anim": "01",
    "animator": "02",
    "array": "03",
    "attr": "04",
    "bool": "05",
    "color": "06",
    "dimen": "07",
    "drawable": "08",
    "id": "09",
    "integer": "0a",
    "interpolator": "0b",
    "layout": "0c",
    "menu": "0d",
    "mipmap": "0e",
    "navigation": "0f",
    "plurals": "10",
    "raw": "11",
    "string": "12",
    "style": "13",
    "transition": "15",
    "xml": "16",
}

def convert_public_xml_to_txt(input_file, output_file, package_name):
    tree = ET.parse(input_file)
    root = tree.getroot()

    with open(output_file, 'w') as f:
        for public_elem in root.iter('public'):
            elem_type = public_elem.get('type')
            elem_name = public_elem.get('name')
            elem_id = modify(public_elem.get('id'), "7f", replacement_dict[elem_type])

            if elem_type and elem_name and elem_id:
                line = f"{package_name}:{elem_type}/{elem_name} = {elem_id}\n"
                f.write(line)

    print(f"Conversion completed. Output file: {output_file}")

# 使用示例
input_xml_file = './public.xml'  # 输入的 public.xml 文件路径
output_txt_file = 'public.txt'  # 输出的 public.txt 文件路径
package_name = 'com.tencent.qqlite'  # 包名

convert_public_xml_to_txt(input_xml_file, output_txt_file, package_name)
