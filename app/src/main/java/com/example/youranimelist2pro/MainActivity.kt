package com.example.youranimelist2pro

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.youranimelist.databinding.ActivityMainBinding

import com.example.youranimelist2pro.objects.Anime
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("AnimeListPro")

        var AnimeList = arrayListOf<Anime>()
        AnimeList.add(Anime("Angel Beats",
            "https://static1.cbrimages.com/wordpress/wp-content/uploads/2019/05/angel-beats.jpg?q=50&fit=contain&w=1140&h=&dpr=1.5",
        12,9.0,"Angel Beats! is set in the environment of a high school in the afterlife, a type of limbo for people who have experienced trauma or hardships in life and must overcome them before passing on and being reincarnated",
            "Science fiction, Fantasy"
            ))

        AnimeList.add(Anime("Naruto",
            "https://img2.hulu.com/user/v3/artwork/993d48dc-d507-47cb-8cca-23e6794f6f66?base_image_bucket_name=image_manager&base_image=f7115a1d-c6da-450b-8e08-4fded7d509f0&size=1200x630&format=jpeg",
            500,8.3,
            "tells the story of Naruto Uzumaki, a young ninja who seeks recognition from his peers and dreams of becoming the Hokage, the leader of his village. ",
            "adventure fiction"
        ))

        AnimeList.add(Anime("Death Note",
            "https://i0.wp.com/dmtalkies.com/wp-content/uploads/2021/01/Death-Note-Light-L-and-Ryuk-Cropped-compressed.jpg",
            37,9.0,"The story follows Light Yagami, a teen genius who discovers a mysterious notebook: the \"Death Note\", which belonged to the Shinigami Ryuk, and grants the user the supernatural ability to kill anyone whose name is written in its pages.",
            "Mystery, Thriller"
        ))
        AnimeList.add(Anime("one piece",
            "https://as01.epimg.net/meristation_en/imagenes/2022/02/17/news/1645059859_923391_1645060061_noticia_normal.jpg",
            969,8.7,"", "adventure fiction, fantasy"
        ))
        AnimeList.add(Anime("dragon ball z",
            "https://wallpapercave.com/wp/wp9212496.jpg",
            153,8.7,"The epic episodic adventure of Goku and the Z Warriors as they defend the Earth and the Universe from super-powered fighters and monsters.",
            "adventure fiction"
        ))

        AnimeList.add(Anime("Attack On Titan",
            "https://www.giantfreakinrobot.com/wp-content/uploads/2014/12/attack-on-titan-970x545.jpg",
            75,8.9,"It is set in a world where humanity lives inside cities surrounded by three enormous walls that protect them from the gigantic man-eating humanoids referred to as Titans; the story follows Eren Yeager, who vows to exterminate the Titans after a Titan brings about the destruction of his hometown",
            "Dark fantasy"
        ))

        AnimeList.add(Anime("Fullmetal Alchemist: Brotherhood",
            "data:image/jpeg;base64,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",
            37,9.7,
            "the story of two alchemist brothers, Edward and Alphonse Elric, who want to restore their bodies after a disastrous failed attempt to bring their mother back to life through alchemy.",
            "adventure fiction, fantasy"
        ))

        AnimeList.add(Anime("Sword Art Online",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNJZPoCbWt4say21osWIF7xHeWZt34wSuenT7cjxpul2sm16Vtq8bsueP_ij8eCGttGX8&usqp=CAU",
            25,7.3,
            "the story of a multiplayer virtual-reality game that takes a deadly turn when players discover they can't escape of their own will but must play to victory or to death.",
            "adventure fiction, science fiction"
        ))


        AnimeList.add(Anime("jojo's bizarre adventure",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgKFcoDXYI21MT4VtS1MAV_lF9MNfHsk5SHQ&usqp=CAU",
            204,8.4,
            "tells the story of the Joestar family, a family whose various members discover they are destined to take down supernatural foes using powers that they possess.",
            "adventure fiction, action fiction"
        ))


        AnimeList.add(Anime("One Punch Man",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGGbQb6FFRqy1QeZN_41SLxfaGZ9GUfYt0HA&usqp=CAU",
            24,8.5,
            "tells the story of Saitama, a superhero who can defeat any opponent with a single punch but seeks to find a worthy opponent after growing bored by a lack of challenge due to his overwhelming strength.",
            "action-comedy, "
        ))


        AnimeList.add(Anime("my hero academia",
            "https://www.crunchyroll.com/imgsrv/display/thumbnail/1200x675/catalog/crunchyroll/f0dfabec20d9f3ce33add1a92d381c70.jpe",
            88,8.3,
            "The series focuses on Izuku Midoriya, a young man who dreams of becoming a Hero despite being bullied by his violent childhood friend Katsuki Bakugo for lacking a Quirk.",
            "adventure fiction, fantasy"
        ))


        AnimeList.add(Anime("Tokyo Ghoul",
            "https://media.comicbook.com/2017/06/tokyo-ghoul-anime-1477547440-16d366ea1f605e55e78bcdffe3b09f5e-1004079.jpeg",
            24,7.8,
            "Tokyo Ghoul is set in an alternate reality where ghouls, creatures that look like normal people but can only survive by eating human flesh, live among the human population in secrecy, hiding their true nature in order to evade pursuit from the authorities.",
            "dark fantasy,thriller"
        ))


        AnimeList.add(Anime("charlotte",
            "https://lh3.googleusercontent.com/2fZkzf3dYyioFqzJWgYpECYPVwktpFYDCQsyYVPQNDmERUT41A2HDDMLrVuPTkvGiaLmio6bDtnl1kgp3NPyNZDaqg=w640-h400-e365-rj-sc0x00ffffff",
            13,7.8,
            "Charlotte follows the story of four students in an alternate reality from Hoshinoumi Academy, a school founded to protect children with special abilities that develop during puberty, as they search for and protect others like them throughout Japan.",
            "fantasy"
        ))


        AnimeList.add(Anime("Hunter x Hunter",
            "https://sportshub.cbsistatic.com/i/2022/05/28/c5eaa231-f36a-425a-bffc-e1876bc01cd4/hunter-x-hunter.jpg",
            148,9.0,
            "The story follows a young boy named Gon Freecss, who was told all his life that both his parents were dead. ... A few days later, Gon and Killua achieve their objective and begin playing Greed Island, an extremely rare and expensive video game with Nen-like properties following some clues about Ging's whereabouts.",
            " adventure fiction, martial arts"
        ))


        AnimeList.add(Anime("No Game No Life",
            "https://img4.hulu.com/user/v3/artwork/467805ea-da6e-4448-986e-0fe60d30a9af?base_image_bucket_name=image_manager&base_image=9123a3ad-05d0-4624-87be-554026b034e2&size=600x338&format=jpeg",
            12,8.1,
            "The series follows a group of human gamers seeking to beat the god of games at a series of boardgames in order to usurp the god's throne.",
            "isekai, fantasy"
        ))

/*
        AnimeList.add(Anime("Kakegurui",
            "https://deathmetalflorist.files.wordpress.com/2019/01/kakegurui.png",
            500,7.2,
            "The story of Kakegurui – Compulsive Gambler takes place at Hyakkaou Private Academy, one of Japan's most prestigious schools where, unlike normal schools, the hierarchy is determined by gambling",
            "gambling, psychological thriller"
        ))


        AnimeList.add(Anime("Toradora!",
            "https://m.media-amazon.com/images/I/71iTBfKZR4L._RI_.jpg",
            25,8.3,
            "revolves around Ryuuji Takasu, despite his gentle personality, his eyes make him look like an intimidating delinquent. Class rearrangements on his second high school year put him together with his best friend, Yusaku Kitamura, and his hidden crush, Minori Kushieda.",
            "romantic comedy"
        ))


        AnimeList.add(Anime("",
            "https://thinpo.com/wp-content/uploads/2020/11/Demon-Slayer-Kimetsu-no-Yaiba-min.jpg",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))


        AnimeList.add(Anime("",
            "",
            500,8.3,
            "",
            ""
        ))
*/

        myRef.setValue(AnimeList)


    }



    }
