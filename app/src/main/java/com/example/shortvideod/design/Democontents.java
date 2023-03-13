package com.example.shortvideod.design;

import com.example.shortvideod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Democontents {
    static List<String> girlsImage = new ArrayList<>(Arrays.asList("https://images.unsplash.com/photo-1506610154363-2e1a8c573d2d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=844&q=80",
            "https://images.unsplash.com/photo-1555703473-5601538f3fd8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=858&q=80",
            "https://images.unsplash.com/photo-1597983073453-ef06cfc2240e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1588671335940-2a6646b6bb0a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=810" +
                    "&q=80",
            "https://images.unsplash.com/photo-1583058905141-deef2de746bb?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=888&q=80"
    ));
    static List<String> boysImage = new ArrayList<>(Arrays.asList(
            "https://images.unsplash.com/photo-1609637082285-1aa1e1a63c16?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1485528562718-2ae1c8419ae2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=858&q=80",
            "https://images.unsplash.com/photo-1552774021-9ebbb764f03e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1629189858155-9eb2649ec778?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1570211776086-5836c8b1e75f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=880&q=80"
    ));


    private Democontents() {
    }


    public static List<Integer> getClothHashtag() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.cloth1);
        hashtag.add(R.drawable.cloth2);
        hashtag.add(R.drawable.cloth3);
        hashtag.add(R.drawable.cloth4);
        hashtag.add(R.drawable.cloth2);
        hashtag.add(R.drawable.cloth1);
        hashtag.add(R.drawable.cloth4);
        hashtag.add(R.drawable.cloth3);
        hashtag.add(R.drawable.cloth2);
        hashtag.add(R.drawable.cloth1);
        return hashtag;
    }

    public static List<Integer> getActivities() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.cloth1);
        hashtag.add(R.drawable.cloth2);
        hashtag.add(R.drawable.cloth3);
        hashtag.add(R.drawable.cloth4);
        return hashtag;
    }

    public static List<Hashtag> getHashtag() {
        ArrayList<Hashtag> hashtag = new ArrayList<>();
        hashtag.add(new Hashtag("Skill"));
        hashtag.add(new Hashtag("Skill on dance"));
        hashtag.add(new Hashtag("Nach meri rani"));
        return hashtag;
    }

    public static List<Likes> getLikes() {
        List<Likes> like = new ArrayList<>();
        like.add(new Likes(girlsImage.get(0), "", "Pranshi gem liked your post."));
        like.add(new Likes(girlsImage.get(1), "", "Lily adams liked your post. "));
        like.add(new Likes(girlsImage.get(2), "", "Miya modi  liked your post. "));
        like.add(new Likes(girlsImage.get(3), "", "Shana gill liked your post. "));
        Collections.shuffle(like);
        return like;
    }

    public static List<GiftList> getGiftNotiList() {
        List<GiftList> giftLists = new ArrayList<>();
        giftLists.add(new GiftList(girlsImage.get(0), "Pranshi gem send gift to you.", giftImg().get(0)));
        giftLists.add(new GiftList(girlsImage.get(1), "Lily adams  send gift to you. ", giftImg().get(2)));
        giftLists.add(new GiftList(girlsImage.get(2), "Miya modi   send gift to you. ", giftImg().get(1)));
        giftLists.add(new GiftList(girlsImage.get(2), "Shana gill  send gift to you. ", giftImg().get(3)));
        Collections.shuffle(giftLists);
        return giftLists;
    }

    public static List<Mentions> getMentions() {
        List<Mentions> mentions = new ArrayList<>();
        mentions.add(new Mentions(girlsImage.get(0), "Pranshi gem mentioned you in a comment.", "", "❤love❤"));
        mentions.add(new Mentions(girlsImage.get(1), "Lily adams  mentioned you in a comment.", "", "༺❉MR. Perfect❉༻"));
        mentions.add(new Mentions(girlsImage.get(2), "Miya modi   mentioned you in a comment.", "", "⚽Sports lover⛳"));
        mentions.add(new Mentions(girlsImage.get(3), "Shana gill  mentioned you in a  comment.", "", "❤️Your looks❤️"));
        Collections.shuffle(mentions);
        return mentions;
    }


    public static List<Mention> getMentionUsre() {
        List<Mention> mentions = new ArrayList<>();
        mentions.add(new Mention("", girlsImage.get(0)));
        mentions.add(new Mention("", girlsImage.get(1)));
        mentions.add(new Mention("", girlsImage.get(2)));
        mentions.add(new Mention("", girlsImage.get(3)));

        return mentions;
    }

    public static List<Integer> getSkillHashtag() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.skill1);
        hashtag.add(R.drawable.skill2);
        hashtag.add(R.drawable.skill3);
        hashtag.add(R.drawable.skill4);
        hashtag.add(R.drawable.skill2);
        hashtag.add(R.drawable.skill1);
        hashtag.add(R.drawable.skill3);
        hashtag.add(R.drawable.skill2);
        hashtag.add(R.drawable.skill4);
        hashtag.add(R.drawable.skill1);
        return hashtag;
    }

    public static List<String> getHashtagGroup() {
        ArrayList<String> hashtag = new ArrayList<>();
        hashtag.add(boysImage.get(0));
        hashtag.add(boysImage.get(1));
        hashtag.add(boysImage.get(2));
        hashtag.add(girlsImage.get(0));
        hashtag.add(girlsImage.get(3));
        hashtag.add(girlsImage.get(2));
        hashtag.add(girlsImage.get(1));
        hashtag.add(boysImage.get(3));
        hashtag.add(girlsImage.get(4));
        hashtag.add(boysImage.get(3));
        hashtag.add(boysImage.get(2));
        hashtag.add(girlsImage.get(3));


        return hashtag;
    }

    public static List<Integer> getUserImg() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.user1);
        hashtag.add(R.drawable.user2);
        hashtag.add(R.drawable.user3);
        hashtag.add(R.drawable.user4);
        return hashtag;
    }

    public static List<Integer> giftGrayImg() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.elephant_big);
        hashtag.add(R.drawable.heart_big);
        hashtag.add(R.drawable.rose_big);
        hashtag.add(R.drawable.aces_big);
        hashtag.add(R.drawable.ruby_big);
        hashtag.add(R.drawable.kindda_big);
        return hashtag;
    }

    public static List<Integer> PostImg() {
        ArrayList<Integer> post = new ArrayList<>();
        post.add(R.drawable.post1);
        post.add(R.drawable.hash_2);
        post.add(R.drawable.hash_1);
        post.add(R.drawable.hash_8);
        post.add(R.drawable.hash_3);
        post.add(R.drawable.hash_4);
        post.add(R.drawable.hash_5);
        return post;
    }

    public static List<Integer> giftImg() {
        ArrayList<Integer> hashtag = new ArrayList<>();
        hashtag.add(R.drawable.elephant_big);
        hashtag.add(R.drawable.heart_big);
        hashtag.add(R.drawable.rose);
        hashtag.add(R.drawable.aces_gray);
        hashtag.add(R.drawable.ruby_gray);
        hashtag.add(R.drawable.kinnda_gray);
        return hashtag;
    }


    public static List<UserPostVideo> getPostVideo() {
        List<UserPostVideo> userpost = new ArrayList<>();

        userpost.add(new UserPostVideo(girlsBio().get(1), girlsImage.get(4)));
        userpost.add(new UserPostVideo("", girlsImage.get(1)));
        userpost.add(new UserPostVideo(girlsBio().get(2), girlsImage.get(2)));
        userpost.add(new UserPostVideo(girlsBio().get(4), girlsImage.get(3)));
        userpost.add(new UserPostVideo(girlsBio().get(3), boysImage.get(0)));
        userpost.add(new UserPostVideo("", boysImage.get(2)));
        userpost.add(new UserPostVideo(girlsBio().get(0), boysImage.get(3)));
        return userpost;
    }

    public static List<Gift> getGiftListoNLY() {
        List<Gift> gift = new ArrayList<>();
        gift.add(new Gift(true, 100, giftGrayImg().get(0), 8, "Elephent", getRandomCoin()));
        gift.add(new Gift(true, 100, giftGrayImg().get(1), 6, "heart", getRandomCoin()));
        gift.add(new Gift(true, 100, giftGrayImg().get(2), 0, "rose", getRandomCoin()));
        gift.add(new Gift(false, 100, giftGrayImg().get(3), 0, "aces", getRandomCoin()));
        gift.add(new Gift(false, 100, giftGrayImg().get(4), 0, "ruby", getRandomCoin()));
        gift.add(new Gift(false, 100, giftGrayImg().get(5), 0, "kindda", getRandomCoin()));
        return gift;
    }

    public static int getRandomCoin() {
        return new Random().nextInt(889) + 111;
    }



    public static List<Gift> getGiftList() {
        List<Gift> gift = new ArrayList<>();
        gift.add(new Gift(true, 100, giftImg().get(0), 8, "", 0));
        gift.add(new Gift(true, 100, giftImg().get(1), 6, "", 0));
        gift.add(new Gift(true, 100, giftImg().get(2), 0, "", 0));
        gift.add(new Gift(false, 100, giftImg().get(3), 0, "", 0));
        gift.add(new Gift(false, 100, giftImg().get(4), 0, "", 0));
        gift.add(new Gift(false, 100, giftImg().get(5), 0, "", 0));
        return gift;
    }


    public static List<Sticker> getStickers() {
        List<Sticker> stickers = new ArrayList<>();
        stickers.add(new Sticker(1, "https://muly.starthub.ltd/storage/demo/stickers/tBYh155Uj846jNB.png"));
        stickers.add(new Sticker(2, "https://muly.starthub.ltd/storage/demo/stickers/5xjouRhyJJul6vG.png"));
        stickers.add(new Sticker(3, "https://muly.starthub.ltd/storage/demo/stickers/VQsIiRGJb1xyR29.png"));
        stickers.add(new Sticker(4, "https://muly.starthub.ltd/storage/demo/stickers/uMupGAtXaI2Yzm6.png"));
        stickers.add(new Sticker(5, "https://muly.starthub.ltd/storage/demo/stickers/6MRpnln3q8DMTuC.png"));
        stickers.add(new Sticker(6, "https://muly.starthub.ltd/storage/demo/stickers/r6oSVjkVNY9Opww.png"));
        stickers.add(new Sticker(7, "https://muly.starthub.ltd/storage/demo/stickers/rcKJ3JIuBT6JQkL.png"));
        stickers.add(new Sticker(8, "https://muly.starthub.ltd/storage/demo/stickers/vtJsNlyEUZvqEQb.png"));
        stickers.add(new Sticker(9, "https://muly.starthub.ltd/storage/demo/stickers/dvRToewsl0vliMw.png"));
        stickers.add(new Sticker(10, "https://muly.starthub.ltd/storage/demo/stickers/9N2gUIUDdwTsPAT.png"));

        return stickers;
    }

    public static List<Song> getSongFiles() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "Rahogi Meri", "Pritam, Arijit Singh", "https://muly.starthub.ltd/storage/demo/covers/BydL9iUJ1wRZAgYpng",
                "https://muly.starthub.ltd/storage/demo/audios/jrmyRx4Uwy3GkVy.aac", 14, ""));

        songs.add(new Song("2", "Coca Cola", "Pritam, Arijit Singh",
                "https://muly.starthub.ltd/storage/demo/covers/ZFpka7K6dxUAQCnpng",
                "https://muly.starthub.ltd/storage/demo/audios/jrmyRx4Uwy3GkVy.aac", 19, ""));

        songs.add(new Song("3", "Savage Love (Laxed - Siren Beat)", "Jawsh 685, Jason Derulo",
                "https://muly.starthub.ltd/storage/demo/covers/ZEybCPyhf0QcUZZpng",
                "https://muly.starthub.ltd/storage/demo/audios/93BahZERK0DOiiq.aac", 28, ""));



        return songs;
    }

    public static List<String> girlsBio() {
        List<String> bios = new ArrayList<>();

        String bio1 = "Money can’t buy happiness. But it can buy Makeup!";
        String bio2 = "Sometimes it’s the princess who kills the dragon and saves the prince.";
        String bio3 = "love..dancing.\uD83D\uDE18\uD83D\uDE18\n" +
                "luv ❤my❤ friends\uD83D\uDC48";
        String bio4 = "\uD83D\uDCF7Like Photography✔\n" +
                "\uD83D\uDC01Animal Lover✔\n" +
                "\uD83C\uDF6CChocolate Lover✔\n" +
                "\uD83D\uDE2DFirst Cry On 11th March✔\n" +
                "\uD83D\uDC8AMedical Student✔\n";
        String bio5 = "I’m a princess \uD83D\uDC96, not because I have a Prince, but because my dad is a king \uD83D\uDC51\n";

        bios.add(bio1);
        bios.add(bio2);
        bios.add(bio3);
        bios.add(bio4);
        bios.add(bio5);

        return bios;

    }

    public static List<String> boysBio() {
        List<String> bios = new ArrayList<>();

        String bio1 = "\uD83D\uDCAFOfficial account\uD83D\uDD10\n" +
                "\uD83D\uDCF7Photography\uD83D\uDCF7\n" +
                "\uD83D\uDE18Music lover\uD83C\uDFB6\n" +
                "⚽Sports lover⛳\n" +
                "\uD83D\uDE0DSports bike lover\n";
        String bio2 = "\uD83D\uDC51Attitude Prince\uD83D\uDC51\n" +
                "\uD83E\uDD1DDosto Ki Shan\uD83D\uDC9C\n" +
                "\uD83D\uDC8FGF Ki Jaan♥️\n" +
                "\uD83D\uDC9EMom Ka Ladla\uD83D\uDC93\n" +
                "\uD83D\uDC99Pappa Ka Pyara\uD83D\uDC99";
        String bio3 = "love..dancing.\uD83D\uDE18\uD83D\uDE18\n" +
                "luv ❤my❤ friends\uD83D\uDC48";
        String bio4 = "\uD83D\uDCF7Like Photography✔\n" +
                "\uD83D\uDC01Animal Lover✔\n" +
                "\uD83C\uDF6CChocolate Lover✔\n" +
                "\uD83D\uDE2DFirst Cry On 11th March✔\n" +
                "\uD83D\uDC8AMedical Student✔\n";
        String bio5 = "༺❉MR. Perfect❉༻\n" +
                "\uD83D\uDCA5King OF 22 May\uD83C\uDF1F\n" +
                "\uD83C\uDFB5Music Addicted\uD83C\uDFB6\n" +
                "\uD83D\uDC9C Photography\uD83D\uDCF8\n" +
                "\uD83D\uDC95Heart Hã¢Kër\uD83D\uDC8C";

        bios.add(bio1);
        bios.add(bio2);
        bios.add(bio3);
        bios.add(bio4);
        bios.add(bio5);

        return bios;
    }

    public static List<User> getUsers(boolean isShuffle) {
        List<User> users = new ArrayList<>(Arrays.asList(new User("Aaliya Mia", girlsBio().get(0), "@aaliya1", "aaliyamia@email.com", girlsImage.get(0), "USA", 1, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "FEMALE"), new User("Lily Adams", girlsBio().get(1), "@Lily", "Lily@email.com", girlsImage.get(1), "UK", 2, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "FEMALE"), new User("Charlotte Bailey", girlsBio().get(2), "@Charlotte", "Bailey@email.com", girlsImage.get(2), "GERMANY", 3, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "FEMALE"), new User("Isabella Kennedy", girlsBio().get(3), "@Isabella", "Kennedy@email.com", girlsImage.get(3), "INDIA", 4, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "FEMALE"), new User("Camila Marshall", girlsBio().get(4), "@Camila", "Marshall@email.com", girlsImage.get(4), "FRANCE", 5, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "FEMALE"), new User("James Carter", boysBio().get(0), "@Carter", "James@email.com", boysImage.get(0), "USA", 1, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "MALE"), new User("Henry Adams", boysBio().get(1), "@Henry", "Adams@email.com", boysImage.get(1), "FRANCE", 2, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "MALE"), new User("Daniel Davidson", boysBio().get(2), "@Davidson", "Daniel@email.com", boysImage.get(2), "INDIA", 3, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "MALE"), new User("Jackson Edwards", boysBio().get(3), "@Jackson", "Edwards@email.com", boysImage.get(3), "GERMANY", 4, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "MALE"), new User("Thomas Bailey", boysBio().get(4), "@Thomas", "Bailey@email.com", boysImage.get(4), "UK", 5, getRandomCoin(), getRandomCoin(), getRandomPostCoint(), getRandomPostCoint(), getRandomCoin(), getRandomPostCoint(), getRandomCoin(), "MALE")));
        if (isShuffle) {
            Collections.shuffle(users);
        }
        return users;
    }

    public static int getRandomPostCoint() {
        return new Random().nextInt(89) + 11;
    }



    public static List<SuggestedUser> getSuggestedUser() {
        List<SuggestedUser> user = new ArrayList<>();
        user.add(new SuggestedUser("Pranshi gems", "@pranshi_2000", girlsImage.get(0), false));
        user.add(new SuggestedUser("Lily adams", "@Lily_Adams", girlsImage.get(1), false));
        user.add(new SuggestedUser("Miya modi", "@miya_modi", girlsImage.get(2), false));
        user.add(new SuggestedUser("Shana gill", "@shana_1999", girlsImage.get(3), false));
        user.add(new SuggestedUser("Rubina rode", "@Rubina_rode", girlsImage.get(4), false));
        return user;
    }

    public static List<UserList> getUserList() {
        List<UserList> user = new ArrayList<>();
        user.add(new UserList("Pranshi gems", "@pranshi_2000", girlsImage.get(0), false));
        user.add(new UserList("Lily adams", "@Lily_Adams", girlsImage.get(1), false));
        user.add(new UserList("Miya modi", "@miya_modi", girlsImage.get(2), false));
        user.add(new UserList("Shana gill", "@shana_1999", girlsImage.get(3), false));

        return user;
    }





    public static List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Pranshi gems", girlsImage.get(0), "2 hour ago", "No matter where I go, I cannot find someone beautiful like you."));
        comments.add(new Comment("Lily adams", girlsImage.get(1), "Just Now", "❤️Your looks make me insane.❤️"));
        comments.add(new Comment("Miya modi", girlsImage.get(2), "5 hour ago", "Such a charming capture✔️✔️✔️✔️"));
        comments.add(new Comment("Shana gill", girlsImage.get(3), "12 hour ago", "Well I think this is often my favorite posture of yours."));


        Collections.shuffle(comments);
        return comments;
    }

    public static List<Reels> getReels() {
        List<Reels> reels = new ArrayList<>();
        reels.add(new Reels(getUsers(true).get(0), "Creativity is my thing, what's yours?", "https://dev.digicean.com/storage/239891192_3061111304125653_779170672042824055_n.mp4", "@Aaliya Mia", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "Creativity is my thing, what's yours?", "https://dev.digicean.com/storage/241681828_894992998067015_8734076864715268332_n.mp4", "@Lily Adams", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "Life is better when you're laughing.", "https://dev.digicean.com/storage/242025003_129510119414535_2313267097805870250_n.mp4", "@Charlotte Bailey", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "If you like this video, it's supposed to bring you good luck for the rest of the day.", "https://dev.digicean.com/storage/242025003_129510119414535_2313267097805870250_n.mp4", "@Isabella Kennedy", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "If life had a soundtrack, this would be my song.", "https://dev.digicean.com/storage/247062863_341024567823973_5333055093789086258_n.mp4", "@Camila Marshall", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "I reel-y got into this whole Instagram Reels thing.", "https://dev.digicean.com/storage/254729432_1108864849935754_4352344584780102107_n.mp4", "@James Carter", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "I'm not going to tell you how long it took me to edit this.", "https://dev.digicean.com/storage/255030357_230359622521878_8463246573248024146_n.mp4", "@Henry Adams", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "It's me, the best dancer on your feed.", "https://dev.digicean.com/storage/247062863_341024567823973_5333055093789086258_n.mp4", "@Daniel Davidson", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "If I’m on your feed, you know it’s gonna be a good day.", "https://dev.digicean.com/storage/241681828_894992998067015_8734076864715268332_n.mp4", "@Jackson Edwards", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "Wedding", "https://dev.digicean.com/storage/255030357_230359622521878_8463246573248024146_n.mp4", "@Thomas Bailey", getRandomPostCoint(), getRandomPostCoint()));
        reels.add(new Reels(getUsers(true).get(0), "Beauty of Bride", "https://dev.digicean.com/storage/239891192_3061111304125653_779170672042824055_n.mp4", "@Daniel Davidson", getRandomPostCoint(), getRandomPostCoint()));
        Collections.shuffle(reels);
        return reels;
    }


    public static List<ChatUser> getChatUsers() {
        List<ChatUser> chatUsers = new ArrayList<>();

        chatUsers.add(new ChatUser("Aaliya Mia", "2 hour ago", "Hello", getHashtagGroup().get(3), false));
        chatUsers.add(new ChatUser("Pranshi gems", "Just Now", "Hey Can we talk ?", getHashtagGroup().get(4), true));
        chatUsers.add(new ChatUser("Lily adams", "Just Now", "I am available now", getHashtagGroup().get(5), true));
        chatUsers.add(new ChatUser("Shana gill", "12 hour ago", "❤ ⊂◉‿◉つ", getHashtagGroup().get(6), false));

        return chatUsers;
    }

    public static List<Chat> getRandomChat() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat("What's yor name ?", "text", Chat.user, 0));
        chats.add(new Chat("Hey do you want chat with me ?", "text", Chat.mainuser, 0));
        chats.add(new Chat("Hmm", "text", Chat.mainuser, 0));
        chats.add(new Chat("What ?", "text", Chat.user, 0));
        chats.add(new Chat("Are you kidding with me?", "text", Chat.user, 0));
        chats.add(new Chat("Do  you have  girlfriend?", "text", Chat.mainuser, 0));
        chats.add(new Chat("hey Dude I am boring now \n so you can chat with me", "text", Chat.user, 0));
        chats.add(new Chat("I am busy right now \n talk to you later", "text", Chat.mainuser, 0));
        chats.add(new Chat("Send me your insta id", "text", Chat.user, 0));
        chats.add(new Chat("Send me your Number", "text", Chat.user, 0));
        chats.add(new Chat("Am i looking Pretty??", "text", Chat.mainuser, 0));

        return chats;

    }


    public static List<Hashtag> getHashtags() {

        List<Hashtag> hashtags = new ArrayList<>();
        hashtags.add(new Hashtag("#Love"));
        hashtags.add(new Hashtag("#Nature"));
        hashtags.add(new Hashtag("#Wedding"));
        hashtags.add(new Hashtag("#Alone"));
        hashtags.add(new Hashtag("#Female"));
        hashtags.add(new Hashtag("#Happy"));
        hashtags.add(new Hashtag("#Music"));
        hashtags.add(new Hashtag("#Smile"));

        Collections.shuffle(hashtags);
        return hashtags;
    }


    public static List<HashtagAuto> getHashtagsAuto() {
        List<HashtagAuto> hashtagAutos = new ArrayList<>();
        hashtagAutos.add(new HashtagAuto("Nature", 100));
        hashtagAutos.add(new HashtagAuto("Wedding", 120));
        hashtagAutos.add(new HashtagAuto("Alone", 90));
        hashtagAutos.add(new HashtagAuto("Female", 80));
        hashtagAutos.add(new HashtagAuto("Happy", 50));
        return hashtagAutos;
    }

    public static List<MentionImg> getMentionSlim() {
        List<MentionImg> mentionslim = new ArrayList<>();
        mentionslim.add(new MentionImg("Parnshi Gems", "Parnshi Gems", girlsImage.get(0)));
        mentionslim.add(new MentionImg("Lily adams  ", "Lily adams  ", girlsImage.get(1)));
        mentionslim.add(new MentionImg("Miya modi   ", "Miya modi   ", girlsImage.get(2)));
        mentionslim.add(new MentionImg("Shana gill  ", "Shana gill  ", girlsImage.get(3)));

        return mentionslim;
    }

    public static List<LocationList> getlocationlist() {
        List<LocationList> location = new ArrayList<>();
        location.add(new LocationList("India"));
        location.add(new LocationList("China"));
        location.add(new LocationList("Canada"));
        location.add(new LocationList("Pakistan"));
        location.add(new LocationList("Afghanistan"));
        location.add(new LocationList("Australia"));
        location.add(new LocationList("New Zealand"));
        return location;
    }

}
