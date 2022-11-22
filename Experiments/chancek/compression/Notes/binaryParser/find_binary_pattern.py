#parse mp3 binary text doc
import operator
binary_patterns = {}
with open('/home/chancek/Desktop/School/CS_309/song_binary.txt', 'r') as file:
    for line in file:
        for word in line.split():
            # print('code:' + word)
            in_binary_patterns = False;
            for pattern in binary_patterns:
                if pattern == word:
                    binary_patterns[pattern] += 1
                    in_binary_patterns = True
            if(in_binary_patterns is False):
                binary_patterns.update({word:1})

    print(binary_patterns)
    # 8 bits 256 choices
    # print(len(binary_patterns))
    max_key = max(binary_patterns.items(), key=operator.itemgetter(1))[0]
    max_value = binary_patterns[max_key]

    print('Max Key: (' + max_key + ', ' + str(max_value) + ')')


