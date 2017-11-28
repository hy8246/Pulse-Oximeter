package edu.wit.mobileapp.listview;

/**
 * Created by haopan on 8/8/16.
 */
public class EvaluationCalculate {
    int sex, age, hb;
    public EvaluationCalculate(int s, int a, int h)
    {
        sex= s;
        age= a;
        hb= h;
    }
    public String calculate()
    {
        if(sex==1)
        {
            if(age>=18&&age<=25)
            {
                if(hb<55&&hb>49)
                {
                    return "Your Body Condition is ATHLETE";
                }
                else if(hb<61)
                {
                    return "Your Body Condition is Excellent";
                }
                else if(hb<65)
                {
                    return "Your Body Condition is Good";
                }
                else if(hb<69)
                {
                    return "Your Body Condition is Above Average";
                }
                else if(hb<73)
                {
                    return "Your Body Condition is Average";
                }
                else if(hb<81)
                {
                    return "Your Body Condition is Below Average";
                }
                else
                {
                    return "Your Body Condition is Poor.  Get Some exercise.";
                }
            }
            else if(age>25&&age<=35)
            {
                {
                    if(hb<54&&hb>49)
                    {
                        return "Your Body is ATHLETE";
                    }
                    else if(hb<61)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<65)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<70)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<74)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<81)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }
            }
            else if(age>=36&&age<=45)
            {
                {
                    if(hb<56&&hb>50)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<62)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<66)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<70)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<75)
                    {
                        return "Your Body is Average";
                    }
                    else if(hb<83)
                    {
                        return "Your Body is Below Average";
                    }
                    else
                    {
                        return "Your Body is Poor.  Get Some exercise.";
                    }
                }
            }
            else if(age>=46&&age<=55)
            {
                {
                    if(hb<55&&hb>49)
                    {
                        return "Your Body is ATHLETE";
                    }
                    else if(hb<61)
                    {
                        return "Your Body is Excellent";
                    }
                    else if(hb<65)
                    {
                        return "Your Body is Good";
                    }
                    else if(hb<70)
                    {
                        return "Your Body is Above Average";
                    }
                    else if(hb<74)
                    {
                        return "Your Body is Below Average";
                    }
                    else
                    {
                        return "Your Body is Poor.  Get Some exercise.";
                    }
                }
            }
            else if(age>=56&&age<=65)
            {
                {
                    if(hb<55&&hb>49)
                    {
                        return "Your Body is ATHLETE";
                    }
                    else if(hb<61)
                    {
                        return "Your Body is Excellent";
                    }
                    else if(hb<65)
                    {
                        return "Your Body is Good";
                    }
                    else if(hb<70)
                    {
                        return "Your Body is Above Average";
                    }
                    else if(hb<74)
                    {
                        return "Your Body is Below Average";
                    }
                    else
                    {
                        return "Your Body is Poor.  Get Some exercise.";
                    }
                }
            }
            else
            {
                {
                    if(hb<55&&hb>49)
                    {
                        return "Your Body is ATHLETE";
                    }
                    else if(hb<61)
                    {
                        return "Your Body is Excellent";
                    }
                    else if(hb<65)
                    {
                        return "Your Body is Good";
                    }
                    else if(hb<70)
                    {
                        return "Your Body is Above Average";
                    }
                    else if(hb<74)
                    {
                        return "Your Body is Below Average";
                    }
                    else
                    {
                        return "Your Body is Poor.  Get Some exercise.";
                    }
                }
            }

        }
        else{
            {
                if(age>=18&&age<=25)
                {
                    if(hb<60&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<61)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<65)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<69)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<73)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<78)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }


                else if(age>25&&age<=35)
                {
                    if(hb<59&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<64)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<68)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<72)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<76)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<82)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }

                else if(age>=36&&age<=45)
                {
                    if(hb<59&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<64)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<69)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<73)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<78)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<84)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }

                else if(age>=46&&age<=55)
                {
                    if(hb<60&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<65)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<69)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<73)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<77)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<83)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }

                else if(age>=56&&age<=65)
                {
                    if(hb<59&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<64)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<68)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<73)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<77)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<83)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }

                else
                {
                    if(hb<59&&hb>54)
                    {
                        return "Your Body Condition is ATHLETE";
                    }
                    else if(hb<64)
                    {
                        return "Your Body Condition is Excellent";
                    }
                    else if(hb<68)
                    {
                        return "Your Body Condition is Good";
                    }
                    else if(hb<72)
                    {
                        return "Your Body Condition is Above Average";
                    }
                    else if(hb<76)
                    {
                        return "Your Body Condition is Average";
                    }
                    else if(hb<84)
                    {
                        return "Your Body Condition is Below Average";
                    }
                    else
                    {
                        return "Your Body Condition is Poor.  Get Some exercise.";
                    }
                }
            }

        }
    }
}

