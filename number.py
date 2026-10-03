# 1                 :   1    |O--------------------------------------------------------------| 
# 10                :   5    |----O----------------------------------------------------------| 3
# 100               :   9    |--------O------------------------------------------------------| 4
# 1000              :   15   |--------------O------------------------------------------------| 6
# 10000             :   18   |-----------------O---------------------------------------------| 3
# 100000            :   22   |---------------------O-----------------------------------------| 4
# 1000000           :   26   |-------------------------O-------------------------------------| 4
# 10000000          :   31   |------------------------------O--------------------------------| 5
# 100000000         :   38   |-------------------------------------O-------------------------| 7
# 1000000000        :   42   |-----------------------------------------O---------------------| 4
# 10000000000       :   44   |-------------------------------------------O-------------------| 2
# 100000000000      :   51   |--------------------------------------------------O------------| 7
# 1000000000000     :   52   |---------------------------------------------------O-----------| 1
# 10000000000000    :   57   |--------------------------------------------------------O------| 5
# 100000000000000   :   63   |--------------------------------------------------------------O| 4

# 1         -  *1         :  *1    |*-----------------------------------------------------------|   2^0
# 2         -   1         :   2    |-O----------------------------------------------------------| 1 2^1
# 4         -   1         :   3    |--O---------------------------------------------------------| 1 2^2
# 8         -   1         :   4    |---O--------------------------------------------------------| 1 2^3
# 16        -  *10        :  *5    |----*-------------------------------------------------------| 1 2^4
# 32        -   10        :   6    |-----O------------------------------------------------------| 1 2^5
# 64        -   10        :   7    |------O-----------------------------------------------------| 1 2^6
# 128       -   100       :   8    |-------O----------------------------------------------------| 1 2^7
# 256       -  *100       :  *9    |--------*---------------------------------------------------| 1 2^8
# 512       -   100       :   10   |---------O--------------------------------------------------| 1 2^9
# 1024      -   1000      :   11   |----------O-------------------------------------------------| 1 2^10
# 2048      -   1000      :   12   |-----------O------------------------------------------------| 1 2^11
# 4096      -   1000      :   13   |------------O-----------------------------------------------| 1 2^12
# 8192      -   1000      :   14   |-------------O----------------------------------------------| 1 2^13
# 16384     -  *10000     :  *15   |--------------*---------------------------------------------| 1 2^14
# 32768     -   10000     :   16   |---------------O--------------------------------------------| 1 2^15
# 65536     -   10000     :   17   |----------------O-------------------------------------------| 1 2^16
# 131072    -  *100000    :  *18   |-----------------*------------------------------------------| 1 2^17
# 262144    -   100000    :   19   |------------------O-----------------------------------------| 1 2^18
# 524288    -   100000    :   20   |-------------------O----------------------------------------| 1 2^19
# 1048576   -   1000000   :   21   |--------------------O---------------------------------------| 1 2^20
# 1048576   -  *10000000  :  *22   |---------------------*--------------------------------------| 1 2^21
# 1048576   -   10000000  :   23   |----------------------O-------------------------------------| 1 2^22
# 1048576   -   10000000  :   24   |-----------------------O------------------------------------| 1 2^23

# y = mx + b
# x, y = 16, 5
# x, y = 512, 10
#  5 =  16m + b   |   b = 5 - 16m
# 10 = 512m + b   |   10 = 512m + 5 - 16m
#                 |   496m = 5   ==   m = 5/496
#                 |   5 = 16(5/496) + b   ==   b = 5 - (80/496)   ==   b = 5 - (5/31)
# y = (5/496)x + (5 - (5/31))

# +1   =   +1
# 00 +1   =   +1
# +1 00 -1 +1   =   00
# +1 00 -1 +1 +1 00 -2 +1   =   00
# +1 00 -1 +1 +1 00 -2 +1 +1 00 -1 +1 +1 00 -3   =   -1
# +1 00 -1 +1 +1 00 -2 +1 +1 00 -1 +1 +1 00 -3 +1 +1 00 -1 +1 +1 00 -2 +1 +1 00 -1 +1 +1 00 -4   =   


import math
import subprocess
import os

def main():
    again = "yes"
    while ("y" in again and "n" not in again) or again in ["sure"]:
        if os.name == "nt":  # Windows
            subprocess.run("cls", shell=True)
        else:  # macOS / Linux
            subprocess.run("clear", shell=True)

        num_range = int(input("What is your number range? (1 to ...) "))
        # num_range = 100

        count = 0
        check = ""

        if num_range == 1:
            factor = 1
        else:
            factor = num_range / 2

        guess = int(factor)
        # 691

        mode = input("""
1. Manual mode
2. Automatic mode
3. Evaluate mode
4. Graph mode
>""").lower()
        if mode in ["manual", "1"]:
            manual(check, factor, guess, count)

        elif mode in ["automatic", "2"]:
            number = int(input("What is your number to be processed? "))
            [count, guess] = auto(factor, guess, count, number)

            if count == 1:
                runs = "run"
            else:
                runs = "runs"

            print(f"\nYour number, {guess}, was found in {count} {runs}.")

        elif mode in ["evaluate", "3"]:
            [hardest_count, hardest_num] = find_hardest_number(factor, guess, num_range)

            print(f"\nHardest Number for range {num_range} is {hardest_num} at {hardest_count} runs.")

        elif mode in ["graph", "4"]:
            graph(num_range)

        again = input("\nKeep going? >").lower()



def manual(check, factor, guess, count):
    while check not in ["yes", "1"]:
        check = input(f"""
Is your number {guess}?
1. Yes
2. Higher
3. Lower
>""").lower().strip()
        
        factor = int(factor / 2)

        if factor == 0:
            factor = 1

        if check in ["higher", "2"]:
            guess += factor

        elif check in ["lower", "3"]:
            guess -= factor

        # print(factor)
        count += 1

    if count == 1:
        tries = "try"
    else:
        tries = "tries"

    print(f"\nYay! I got it in {count} {tries}!")


def auto(factor, guess, count, number):
    while guess != number:
        factor = int(factor / 2)
        # print(guess)

        if factor == 0:
            factor = 1

        if guess < number:
            guess += factor

        elif guess > number:
            guess -= factor

        count += 1
    
    count += 1

    return [count, guess]


def find_hardest_number(factor, guess, num_range):
    hardest_num = -1
    hardest_count = -1

    for i in range(num_range):
        count = 0

        num = i + 1
        guess = int(factor)

        [count, guess] = auto(factor, guess, count, num)

        if count > hardest_count:
            hardest_count = count
            hardest_num = guess

    return [hardest_count, hardest_num]

   

def graph(num_range):
    upper_lower = input("""
Graph Mode:
1. Upper
2. Lower
3. Balanced
4. All
>""").lower()
    
    too_high = False
    # if num_range > 1000:
    #     too_high = True
    
    significant = {}

    if upper_lower in ["all", "4"]:
        variation = int(input("What is your integer range for signicant variation? "))
        too = input("""
Display Full Graph?
1. Yes
2. No
>""").lower()
        if too in ["no", "2"]:
            too_high = True
    
    if os.name == "nt":  # Windows
        subprocess.run("cls", shell=True)
    else:  # macOS / Linux
        subprocess.run("clear", shell=True)
    
    if upper_lower in ["all", "4"]:
        print("Range  -  Hardest Count Round Down - Hardest Count Round Evenly - Hardest Count Round Up |GRAPH|")
    else:
        print("Range  -  Hardest Count |GRAPH|")

    for i in range(num_range):
        num = i+1

        exp = False
        exp_num = num

        if exp_num == 1:
            exp = True

        while exp_num % 2 == 0:
            exp_num = exp_num // 2
            
            if exp_num == 1:
                exp = True
                break

        if num == 1:
            if upper_lower in ["all", "4"]:
                high_factor = 1
                low_factor = 1
                even_factor = 1
            else:
                i_factor = 1
        else:
            if upper_lower in ["upper", "1"]:
                i_factor = math.ceil(num / 2)
            elif upper_lower in ["lower", "2"]:
                i_factor = math.floor(num / 2)
            elif upper_lower in ["balanced", "3"]:
                i_factor = round(num / 2)
            elif upper_lower in ["all", "4"]:
                high_factor = math.ceil(num / 2)
                low_factor = math.floor(num / 2)
                even_factor = round(num / 2)

        if upper_lower in ["all", "4"]:
            sign = False

            high_guess = high_factor
            low_guess = low_factor
            even_guess = even_factor

            [high_hardest_count, high_hardest_num] = find_hardest_number(high_factor, high_guess, num)
            [low_hardest_count, low_hardest_num] = find_hardest_number(low_factor, low_guess, num)
            [even_hardest_count, even_hardest_num] = find_hardest_number(even_factor, even_guess, num)

            fill = "-"*50
            fill = fill[:high_hardest_count - 1] + ">" + fill[high_hardest_count:]
            fill = fill[:low_hardest_count - 1] + "<" + fill[low_hardest_count:]
            fill = fill[:even_hardest_count - 1] + "X" + fill[even_hardest_count:]
            
            if abs(even_hardest_count - low_hardest_count) >= variation or abs(even_hardest_count - high_hardest_count) >= variation:
                significant[f"{num:00005}"] = [high_hardest_count, low_hardest_count, even_hardest_count, fill]
                sign = True

            # if low_hardest_count % 2 == 0:
            #     low_hardest_count = f"\033[34m{low_hardest_count:02}\033[0m"
            # elif low_hardest_count % 2 == 1:
            #     low_hardest_count = f"\033[31m{low_hardest_count:02}\033[0m"

            # if even_hardest_count % 2 == 0:
            #     even_hardest_count = f"\033[34m{even_hardest_count:02}\033[0m"
            # elif even_hardest_count % 2 == 1:
            #     even_hardest_count = f"\033[31m{even_hardest_count:02}\033[0m"

            # if high_hardest_count % 2 == 0:
            #     high_hardest_count = f"\033[34m{high_hardest_count:02}\033[0m"
            # elif high_hardest_count % 2 == 1:
            #     high_hardest_count = f"\033[31m{high_hardest_count:02}\033[0m"

            low = 1 + (low_hardest_count % 10)
            even = 1 + (even_hardest_count % 10)
            high = 1 + (high_hardest_count % 10)

            if low >= 7:
                low -= 6
            if even >= 7:
                even -= 6
            if high >= 7:
                high -= 6
            
            low_hardest_count = f"\033[3{low}m{low_hardest_count:02}\033[0m"
            even_hardest_count = f"\033[3{even}m{even_hardest_count:02}\033[0m"
            high_hardest_count = f"\033[3{high}m{high_hardest_count:02}\033[0m"


            if exp == True:
                if too_high == False:
                    if sign == True:
                        print(f"\033[1;3;4;31m{num:00005}\033[0m - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} \033[31m|{fill}|\033[0m")
                    else:
                        print(f"{num:00005} - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} \033[31m|{fill}|\033[0m")

                else:
                    print(f"{((num / num_range) * 100):.9f}%")
                    print("\033[F", end="")
                    if sign == True:
                        print(f"\033[1;3;4;31m{num:000000009}\033[0m - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} \033[31m|{fill}|\033[0m")
            else:
                if too_high == False:
                    if sign == True:
                        print(f"\033[1;3;4;31m{num:00005}\033[0m - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} |{fill}|")
                    else:
                        print(f"{num:00005} - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} |{fill}|")

                else:
                    print(f"{((num / num_range) * 100):.9f}%")
                    print("\033[F", end="")
                    if sign == True:
                        print(f"\033[1;3;4;31m{num:000000009}\033[0m - {low_hardest_count}-{even_hardest_count}-{high_hardest_count} |{fill}|")

        else:
            # if hardest_count % 2 == 0:
            #     hardest_count = f"\033[34m{hardest_count:02}\033[0m"
            # elif hardest_count % 2 == 1:
            #     hardest_count = f"\033[31m{hardest_count:02}\033[0m"

            i_guess = i_factor

            [hardest_count, hardest_num] = find_hardest_number(i_factor, i_guess, num)
            fill = "-"*50
            fill = fill[:hardest_count - 1] + "X" + fill[hardest_count:]

            count = 1 + (hardest_count % 10)

            if count >= 7:
                count -= 6

            hardest_count = f"\033[3{count}m{hardest_count:02}\033[0m"
            if exp == True:
                if too_high == False:
                    print(f"{num:00005} - {hardest_count} \033[31m|{fill}|\033[0m")
            else:
                if too_high == False:
                    print(f"{num:00005} - {hardest_count} |{fill}|")
                

    if too_high == False:
        if upper_lower in ["all", "4"]:
            if significant != {}:
                print()
                for x in significant:
                    high = significant[x][0]
                    low = significant[x][1]
                    even = significant[x][2]
                    fill = significant[x][3]

                    exp = False
                    exp_num = int(x)
                    while exp_num // 2 == 0:
                        if exp_num == 1:
                            exp = True
                            break

                        exp_num = exp_num // 2

                    if exp == True:
                        print(f"Range {x}: Variation {abs(even-low)}-{abs(even-high)} \033[31m|{fill}|\033[0m")
                    else:
                        print(f"Range {x}: Variation {abs(even-low)}-{abs(even-high)} |{fill}|")
        


main()