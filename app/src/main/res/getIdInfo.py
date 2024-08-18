import xml.etree.ElementTree as ET

input_xml_file = '../apkhook/res/values/public.xml'

types = []
for public_elem in ET.parse(input_xml_file).getroot().iter('public'):
    elem_type = public_elem.get('type')
    if elem_type in types:
        continue
    types.append(elem_type)
    elem_id = public_elem.get('id')[4:6]
    print(f'"{elem_type}": "{elem_id}",')
